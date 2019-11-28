package com.gslab.parivahan.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeoApiContext.Builder;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PlacesApi;
import com.google.maps.QueryAutocompleteRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AutocompletePrediction;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Distance;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.gslab.parivahan.dao.IRideDao;
import com.gslab.parivahan.dao.IRideRequestDao;
import com.gslab.parivahan.dbmodel.RideRequest;
import com.gslab.parivahan.dbmodel.User;
import com.gslab.parivahan.exceptions.NoRideRequestFoundException;
import com.gslab.parivahan.exceptions.NoRidesFoundException;
import com.gslab.parivahan.exceptions.NotificationException;
import com.gslab.parivahan.model.DirectionPaths;
import com.gslab.parivahan.model.EmailMessageBody;
import com.gslab.parivahan.model.LatLngDetail;
import com.gslab.parivahan.model.Ride;
import com.gslab.parivahan.model.RideParams;
import com.gslab.parivahan.model.RideRequestVO;
import com.gslab.parivahan.model.RideSearch;
import com.gslab.parivahan.model.RideSearchCriteria;
import com.gslab.parivahan.model.ShuttleScheduleVO;
import com.gslab.parivahan.util.DateUtil;
import com.gslab.parivahan.util.EmailValidator;
import com.gslab.parivahan.util.MessageTemplates;
import com.gslab.parivahan.util.PolyUtil;
import com.gslab.parivahan.util.RideConstants;
import com.gslab.parivahan.util.Vehicle;

@Service
public class DirectionsService implements IDirectionsService {

	public static final int DEFAULT_SEARCH_RADIUS_METERS = 5000;

	@Autowired
	IRideDao rideDao;
	
	@Autowired
	IRideRequestDao requestDao;
	
	@Autowired
	IRideRequestService rideRequestService;

	@Autowired
	private NotificationService notificationService; 
	
	@Autowired
	private Environment env;
	
	@Autowired
	private InviteCreationService ics;
	
	private static int BASE_KM_PRICE;

    private static final Logger log = LoggerFactory.getLogger(DirectionsService.class);

	private GeoApiContext context;

	static final String STRING_SPACE = " ";

	public static int MINIMUM_RIDE_CHARGE;
	
	public GeoApiContext getContext() {
		return context;
	}

	public void setContext(final GeoApiContext context) {
		this.context = context;
	}

	@PostConstruct
	private void init() {
		final Builder contextBuilder = new Builder();
		contextBuilder.apiKey(env.getProperty("parivahan.ride.map.key"));
		MINIMUM_RIDE_CHARGE = Integer.parseInt(env.getProperty("parivahan.ride.minimum.charge"));
		BASE_KM_PRICE = Integer.parseInt(env.getProperty("parivahan.ride.baserate.per.km"));
		this.setContext(contextBuilder.build());
	}

	@Override
	public DirectionsResult getDirections(final String startLocation, final String endLocation,
			final boolean isWalking, boolean alternativeRoutes) {
		 log.info("getDirections: startLocation:" + startLocation + " , endLocation: " + endLocation);
		final long startms = System.currentTimeMillis();
		final DirectionsApiRequest direction = DirectionsApi.getDirections(this.getContext(), startLocation,
				endLocation).region(env.getProperty("parivahan.ride.search.default.country"));
		if (isWalking) {
			direction.mode(TravelMode.WALKING);
		}
		if(alternativeRoutes) {
			direction.alternatives(true);
		}
        DirectionsResult response = null;
        try {
            response = direction.await();
        }
        catch (ApiException | InterruptedException | IOException e) {
            log.error("Cant get directions :", e);
            e.printStackTrace();
        }
        log.info("##Time taken for Direction results API ::" + (System.currentTimeMillis() - startms));
		return response;
	}

	@Override
	public DistanceMatrix getDistanceMatrix(final String[] origins, final String[] destinations) {
		log.info("getDistanceMatrix: origins:" + origins + " , destinations: " + destinations);
        final long startms = System.currentTimeMillis();
		final DistanceMatrixApiRequest distanceMatrixRequest = DistanceMatrixApi.getDistanceMatrix(context, origins,
				destinations);
		DistanceMatrix response = null;
        try {
            response = distanceMatrixRequest.await();
        }
        catch (ApiException | InterruptedException | IOException e) {
            log.error("error in getDistanceMatrix :", e);
        }
        log.info("##Time taken for DistanceMatrix results API ::" + (System.currentTimeMillis() - startms));
		return response;
	}

	@Override
	public List<LatLngDetail> getClosestPointsForARide(final String rideEncodedPolyline, final RideSearchCriteria rideSearchCriteria) {
		final List<LatLng> reducedRidePoints = reduceLatLngPoints(new EncodedPolyline(rideEncodedPolyline).decodePath());

		// origin closest point
		final String[] startPoint = { rideSearchCriteria.getStartLocation() };
		String[] rideDirectionPoints = latLngToStringArray(reducedRidePoints);
		LatLngDetail closestOriginPoint = getClosestPointDetail(startPoint, rideDirectionPoints, reducedRidePoints);

		// destination closest point
		String[] endPoint = { rideSearchCriteria.getEndLocation() };
		LatLngDetail closestDestinationPoint = getClosestPointDetail(endPoint, rideDirectionPoints, reducedRidePoints);
		
		if(closestOriginPoint.getIndex()>closestDestinationPoint.getIndex()) {
			//Ride is in reverse direction
			return null;
		}
		
		return Arrays.asList(closestOriginPoint,closestDestinationPoint);
	}
	
	LatLngDetail getClosestPointDetail(String[] currentLocations, String[] origins,List<LatLng> latLngs) {
		DistanceMatrix distanceMatrix = this.getDistanceMatrix(currentLocations, origins);
		if (distanceMatrix == null) {
			throw new RuntimeException("Cannot get distances from input locations !!");
		}
		TreeMap<Distance, Integer> distanceMap = new TreeMap<>(new DistanceComparator());
		for (int i = 0; i < distanceMatrix.rows[0].elements.length; i++) {
			distanceMap.put(distanceMatrix.rows[0].elements[i].distance, i);
		}
		LatLngDetail latLngDetail=new LatLngDetail();
		latLngDetail.setDistance(distanceMap.firstEntry().getKey());
		Integer keyIndex = distanceMap.firstEntry().getValue();
		latLngDetail.setLatLng(latLngs.get(keyIndex));
		latLngDetail.setIndex(keyIndex);
		return latLngDetail;
	}

	class DistanceComparator implements Comparator<Distance> {

		@Override
		public int compare(final Distance d1, final Distance d2) {
			if (d1.inMeters > d2.inMeters) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	@Override
	public Ride addUserRide(final Ride userRide, boolean showDetailsOnly) {

		LatLng startLatLng;
		LatLng endLatLng;
		String encodedPolyline;

		Date currentDate = new Date();
		Date configuredDate = DateUtil.addDays(currentDate,
				Integer.parseInt(env.getProperty("parivahan.ride.configurable.tooffer")));
		Date rideStartDate = DateUtil.parseDate(DateUtil.DATE_FORMATTER_DATE_AND_TIME, userRide.getDepartureDate());
		if (rideStartDate.getTime() >= configuredDate.getTime()) {
			throw new RuntimeException("Ride start date can not be more than "
					+ env.getProperty("parivahan.ride.configurable.tooffer") + " days");
		} else if (rideStartDate.getTime() < new Date().getTime()) {
			throw new RuntimeException("Ride start date can not be less than current date and time");
		}
		//Adding Shuttle ride
		if (Vehicle.SHUTTLE.toString().equalsIgnoreCase(userRide.getVehicle().toString())) {
			addShuttleRide(userRide, RideConstants.GSLAB_BANER, RideConstants.ENCODED_PATH_BANER_ADISA,
					RideConstants.GSLAB_ADISA, RideConstants.ENCODED_PATH_ADISA_BANER);
			return userRide;
		}

		if (StringUtils.isNotBlank(userRide.getSelectedRidePath())) {
			// User has selected a path from map
			DirectionsResult allRoutes = this.getDirections(userRide.getStartLocation(), userRide.getEndLocation(),
					false, true); // returns all routes
			if (allRoutes == null) {
				throw new RuntimeException("Directions not found for input location");
			}
			DirectionsRoute route = getDirectionRouteByRouteSummary(allRoutes, userRide.getSelectedRidePath());
			if (route == null) {
				// using default route if summary doesnot matches with any route
				route = allRoutes.routes[0];
			}
			startLatLng = route.legs[0].startLocation;
			endLatLng = route.legs[0].endLocation;
			encodedPolyline = route.overviewPolyline.getEncodedPath();
			int charges = Math.round((((Float.valueOf(route.legs[0].distance.inMeters) / 1000) * BASE_KM_PRICE)));
			userRide.setCharges(charges >= MINIMUM_RIDE_CHARGE ? charges : MINIMUM_RIDE_CHARGE);
		} else {
			// User has not selected a path from map, so calling directions api which
			// returns single efficient path
			final DirectionsResult directionResponse = getDirections(userRide.getStartLocation(),
					userRide.getEndLocation(), false, false); // returns single route
			if (directionResponse == null || directionResponse.routes.length == 0) {
				throw new RuntimeException("Can`t find the input location !!");
			}
			startLatLng = directionResponse.routes[0].legs[0].startLocation;
			endLatLng = directionResponse.routes[0].legs[0].endLocation;
			encodedPolyline = directionResponse.routes[0].overviewPolyline.getEncodedPath();
			int charges = Math.round(
					(((Float.valueOf(directionResponse.routes[0].legs[0].distance.inMeters) / 1000) * BASE_KM_PRICE)));
			userRide.setCharges(charges >= MINIMUM_RIDE_CHARGE ? charges : MINIMUM_RIDE_CHARGE);
		}

		userRide.setStartCoordinate(startLatLng.toString());
		userRide.setEndCoordinate(endLatLng.toString());
		userRide.setEncodedPath(encodedPolyline);
		userRide.setEndLatLng(endLatLng);
		userRide.setStartLatLng(startLatLng);

		com.gslab.parivahan.dbmodel.Ride ride = null;
		if (!showDetailsOnly) { // Only show details to user like cost of the ride. Not persisting it yet
			ride = rideDao.addUserRide(new com.gslab.parivahan.dbmodel.Ride(userRide));
			userRide.setId(ride.getRideId());
			try {
				notifyUserWithRideDetails(ride, ride.getUser(),
						String.valueOf(Math.round((userRide.getCharges() * 3) / 4)));
			} catch (Exception e) {
				log.error("Failed to send notification to user with ride details", e);
			}
		}
		userRide.setCharges(Math.round((userRide.getCharges() * 3) / 4));

		return userRide;
	}

	private void addShuttleRide(final Ride userRide, LatLng GslabBaner, String encodedPathBanerToAdisa,
			LatLng GslabAdisa, String encodedPathAdisaToBaner) {
		com.gslab.parivahan.dbmodel.Ride ride = null;
		if(userRide.getStartLocation().contains("Baner")) {
			userRide.setStartCoordinate(GslabBaner.toString());
			userRide.setStartLatLng(GslabBaner);
			userRide.setEndLatLng(GslabAdisa);
			userRide.setEndCoordinate(GslabAdisa.toString());
			userRide.setEncodedPath(encodedPathBanerToAdisa);
		}else {
			userRide.setStartCoordinate(GslabAdisa.toString());
			userRide.setStartLatLng(GslabAdisa);
			userRide.setEndLatLng(GslabBaner);
			userRide.setEndCoordinate(GslabBaner.toString());
			userRide.setEncodedPath(encodedPathAdisaToBaner);
		}
		userRide.setCharges(0);
		ride = rideDao.addUserRide(new com.gslab.parivahan.dbmodel.Ride(userRide));
		userRide.setId(ride.getRideId());
	}
	
	private DirectionsRoute getDirectionRouteByRouteSummary(DirectionsResult allRoutes, String summary) {
		DirectionsRoute[] routes = allRoutes.routes;
		for (int i = 0; i < routes.length; i++) {
			// routes[i].summary,routes[i].overviewPolyline.decodePath()
			if (summary.equals(routes[i].summary)) {
				return routes[i];
			}
		}
		return null;
	}

	@Override
	public void deleteUserRide(Integer offerCode)
			throws NoRidesFoundException, NotificationException, NoRideRequestFoundException {
		com.gslab.parivahan.dbmodel.Ride ride = rideDao.getRideByRideOfferCode(offerCode);
		if(ride==null) {
			throw new RuntimeException("No rides found");
		}
		List<RideRequest> rideRequests = rideRequestService.getRideRequestByOfferCode(offerCode);
		if (rideRequests != null) {
			for (RideRequest rideRequest : rideRequests) {
				rideRequestService.deleteRequest(rideRequest);
				try {
					sendNotificationOnRideCancelToRider(rideRequest, ride);
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
		 
		rideDao.deleteRideByOfferCode(offerCode);
		try {
			sendNotificationOnRideCancelToHost(ride);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}
		
	@Override
	public List<Ride> getFilteredUserRides(RideSearchCriteria rideSearchCriteria) {
		Date departureDate = null; 
		if (null != rideSearchCriteria.getDate()) {
			departureDate = DateUtil.parseDate(DateUtil.DATE_FORMATTER_DATE_AND_TIME, rideSearchCriteria.getDate());
		}
		List<com.gslab.parivahan.dbmodel.Ride> rides = null; 
		if(null == departureDate) {
			//TODO temporary fallback mechanism in case no rides found based on the date criteria owing to date parsing issues. Remove after successful tests.
			rides = rideDao.getAllRides();
		} else {
			try {
				// create max date from departure date by adding days and without time stamp. 
				Date searchLimitDate = DateUtil.addDays(departureDate, 1);
				Date searchLimitDateWithoutTime = DateUtil.removeTimestampFromDate(searchLimitDate);
				log.info("getFilteredUserRides departureDate: "+departureDate+" searchlimitDate: "+ searchLimitDateWithoutTime);
				rides = rideDao.getRidesForGivenDate(departureDate, searchLimitDateWithoutTime, rideSearchCriteria.getUserEmail());
			} catch (ParseException e) {
				log.error(e.getMessage(), e);
			}
		}
		if (rides == null || CollectionUtils.isEmpty(rides)) {
			throw new RuntimeException("No rides available right now!");
		}
		List<Ride> ridesVO = new ArrayList<>();
		for (com.gslab.parivahan.dbmodel.Ride ride : rides) {
			ridesVO.add(new Ride(ride));
		}
		return ridesVO;
	}

	public List<Ride> getUserRides() {
    	List<com.gslab.parivahan.dbmodel.Ride> rides = rideDao.getAllRides();
    	if(rides.isEmpty()) {
			throw new RuntimeException("No rides found");
		}
		List<Ride> ridesVO = new ArrayList<>();
		for (com.gslab.parivahan.dbmodel.Ride ride : rides) {
			Ride rideVO = new Ride(ride);
			rideVO.setCharges(Math.round((rideVO.getCharges()*3)/4));
			ridesVO.add(new Ride(ride));
		}
		return ridesVO;
	}
    

	/*private Map<Distance, Integer> closestRides(final List<LatLng> origins, final List<LatLng> destination) {
		final Map<Distance, Integer> distanceVspoints = new LinkedHashMap<>();
		final String[] originsInString = latLngToStringArray(origins);
		final String[] destinationInString = latLngToStringArray(destination);
		final DistanceMatrix distanceMatrix = this.getDistanceMatrix(originsInString, destinationInString);
		for (int i = 0; i < distanceMatrix.rows.length; i++) {
			for (int j = 0; j < distanceMatrix.rows[i].elements.length; j++) {
				distanceVspoints.put(distanceMatrix.rows[i].elements[j].distance, i);
			}
		}
		return distanceVspoints;
	}*/

	@Override
	public List<RideSearch> searchRides(RideSearchCriteria rideSearchCriteria)  {
		List<Ride> allRides = null;
		if(StringUtils.isBlank(rideSearchCriteria.getUserEmail())) {
			throw new RuntimeException("Email Id can not be blank");
		}
		if(!EmailValidator.validateEmail(rideSearchCriteria.getUserEmail())) {
			throw new RuntimeException("Email id is not valid");
		}
		allRides = this.getFilteredUserRides(rideSearchCriteria);
		log.info(allRides.size() + " rides found after filtering");

		try {
			allRides=filterRidesWithOriginAndDestination(rideSearchCriteria, allRides);
		} catch (ApiException | InterruptedException | IOException e1) {
			e1.printStackTrace();
		}
		log.info(allRides.size()+" rides found after filtering with distance threshold :1000m");
		if (CollectionUtils.isEmpty(allRides)) {
			throw new RuntimeException("No rides available right now!");
		}
		
		final List<RideSearch> rideSearchResults = new ArrayList<RideSearch>();
		
		for (final Ride eachRide : allRides) {
			final RideSearch rideSearchResult = getSearchResultForARide(rideSearchCriteria, eachRide);
			if(rideSearchResult!=null) {
				rideSearchResults.add(rideSearchResult);
			}
			
			
			//TODO Additional walking directions disabled for now
			// final DirectionsResult walkingDirections = this.getDirections(startLocation,
			// midLocationPoint.toString(), true); //move in details api

			// final String walkingPath =
			// walkingDirections.routes[0].overviewPolyline.getEncodedPath();
			// final String walkingDuration =
			// walkingDirections.routes[0].legs[0].duration.humanReadable;

			// rideSearchResult.setWalkingEncodedPath(walkingPath);
			// rideSearchResult.setWalkingDuration(walkingDuration);

			// rideSearchResult.setTravelDuration(String.valueOf(walkingDirections.routes[0].legs[0].duration.inSeconds
			// + finalDrivingDirections.routes[0].legs[0].duration.inSeconds));

		}
		if (CollectionUtils.isEmpty(rideSearchResults)) {
			throw new RuntimeException("No rides available right now!");
		}
		Collections.sort(rideSearchResults, new SearchComparator());
		return rideSearchResults;
	}

	@Override
	public RideSearch getSearchResultForARide(RideSearchCriteria rideSearchCriteria, final Ride eachRide) {
		final RideSearch rideSearchResult = new RideSearch(eachRide);

		final List<LatLngDetail> shortestPoints = this.getClosestPointsForARide(eachRide.getEncodedPath(), rideSearchCriteria);
		if(shortestPoints==null) {
			return null;
		}
		LatLng shortestOriginPoint = shortestPoints.get(0).getLatLng();
		Distance originPointDistance = shortestPoints.get(0).getDistance();

		LatLng shortestDestinationPoint = shortestPoints.get(1).getLatLng();
		Distance destinationPointDistance = shortestPoints.get(1).getDistance();
		log.info("searchRides : shortest point found ORIGIN: " + shortestOriginPoint + " Destination : "
				+ shortestDestinationPoint);

		rideSearchResult.setRiderJoiningPointCoordinate(shortestOriginPoint);
		rideSearchResult.setRiderEndPointCoordinate(shortestDestinationPoint);
		
		rideSearchResult.setOriginWalkingDistance(originPointDistance.humanReadable);
		rideSearchResult.setOriginWalkingDistanceInMeters(originPointDistance.inMeters);

		rideSearchResult.setDestinationWalkingDistance(destinationPointDistance.humanReadable);
		rideSearchResult.setDestinationWalkingDistanceInMeters(destinationPointDistance.inMeters);

		final DirectionsResult finalDrivingDirections = this.getDirections(shortestOriginPoint.toString(),
				shortestDestinationPoint.toString(), false, false);

		Distance rideDistance = finalDrivingDirections.routes[0].legs[0].distance;
		rideSearchResult.setTotalRideDistance(rideDistance.humanReadable);
		if (eachRide.getVehicle().toString().equalsIgnoreCase(Vehicle.BIKE.toString())) {
			rideSearchResult.setTotalChargesForRide(
					(BASE_KM_PRICE > Math.round((Float.valueOf(rideDistance.inMeters) / 1000) * BASE_KM_PRICE) / 2)
							? BASE_KM_PRICE:Math.round((Float.valueOf(rideDistance.inMeters) / 1000) * BASE_KM_PRICE) /2);
		} else if (eachRide.getVehicle().toString().equalsIgnoreCase(Vehicle.CAR.toString())) {
			rideSearchResult.setTotalChargesForRide(
					(BASE_KM_PRICE > Math.round((Float.valueOf(rideDistance.inMeters) / 1000) * BASE_KM_PRICE) / 4)
							? BASE_KM_PRICE:Math.round((Float.valueOf(rideDistance.inMeters) / 1000) * BASE_KM_PRICE) / 4);
		}
		return rideSearchResult;
	}

    private List<Ride> filterRidesWithOriginAndDestination(RideSearchCriteria rideSearchCriteria, List<Ride> allRides) throws ApiException,InterruptedException,IOException {
    	LatLng orgLatLng = null;
    	LatLng destLatLng = null;
    	List<Ride> filteredRides=new ArrayList<>();
    	
    	long threshold = 1000;
    	try {
			 orgLatLng= getGeocodedLatLngFromAddress(rideSearchCriteria.getStartLocation());
			 destLatLng= getGeocodedLatLngFromAddress(rideSearchCriteria.getEndLocation());
			 DirectionsResult direction = getDirections(rideSearchCriteria.getStartLocation(), rideSearchCriteria.getEndLocation(), false,false);
			 long distance = direction.routes[0].legs[0].distance.inMeters;
			 if(distance < 10000) {
				 threshold = Long.parseLong(env.getProperty("parivahan.ride.search.threshold.0To10km"));
			 }else if(distance >= 10000 && distance < 50000) {
				 threshold = Long.parseLong(env.getProperty("parivahan.ride.search.threshold.10To50km"));
			 }else if(distance >= 50000 && distance < 100000) {
				 threshold = Long.parseLong(env.getProperty("parivahan.ride.search.threshold.50To100km"));
			 }else if(distance > 100000){
				 threshold = Long.parseLong(env.getProperty("parivahan.ride.search.threshold.100andabove"));
			 }
			 
		} catch (IOException e) {
			throw new RuntimeException("Cannot find origin/destination location!");
		}catch(InterruptedException e) {
			throw new RuntimeException(e.getMessage());
		}catch (ApiException e) {
			throw new RuntimeException(e.getMessage());
		}
    	for (Ride ride : allRides) {
    		List<LatLng> rideLatlngs = new EncodedPolyline(ride.getEncodedPath()).decodePath();
			//Check if user origin and destination point is in range of current ride polyline 
    		if(PolyUtil.isLocationOnEdge(orgLatLng, rideLatlngs, true,threshold) && PolyUtil.isLocationOnEdge(destLatLng, rideLatlngs, true,threshold)) {
				filteredRides.add(ride);
			}
		}
		return filteredRides;
    	
	}

	@Override
    public RideSearch getFilteredRidesWithParams(final String rideid, final RideParams rideParams){
        final Ride ride = getRideById(rideid);
        if (ride == null) {
            throw new RuntimeException("Ride not found by id : " + rideid);
        }
        final DirectionsResult walkingDirections = this.getDirections(rideParams.getRiderJoiningPointCoordinate(), rideParams.getRiderJoiningPointCoordinate(), true, false);
        final String walkingPath = walkingDirections.routes[0].overviewPolyline.getEncodedPath();
        final String walkingDuration = walkingDirections.routes[0].legs[0].duration.humanReadable;

        final RideSearch rideSearchResult = new RideSearch(ride);
        populateRideSearchWithInputRideParams(rideSearchResult, rideParams);
        rideSearchResult.setWalkingEncodedPath(walkingPath);
        rideSearchResult.setWalkingDurationInSeconds(walkingDuration);
        return rideSearchResult;
    }

    private void populateRideSearchWithInputRideParams(final RideSearch rideSearchResult, final RideParams rideParams) {
        rideSearchResult.setDestinationWalkingDistance(rideParams.getDestinationWalkingDistance());
        rideSearchResult.setDestinationWalkingDistanceInMeters(rideParams.getDestinationWalkingDistanceInMeters());
        rideSearchResult.setOriginWalkingDistance(rideParams.getOriginWalkingDistance());
        rideSearchResult.setOriginWalkingDistanceInMeters(rideParams.getOriginWalkingDistanceInMeters());
        rideSearchResult.setTotalChargesForRide(rideParams.getTotalChargesForRide());
        rideSearchResult.setTotalRideDistance(rideParams.getTotalRideDistance());
        rideSearchResult.setTotalTravelDurationInSeconds(rideParams.getTotalTravelDurationInSeconds());
    }

   
    private Ride getRideById(final String rideId) {
        final List<Ride> rides = getUserRides();
        for (final Ride ride : rides) {
            if (ride.getId().equals(rideId)) {
                return ride;
            }
        }
        return null;
    }

    private List<LatLng> reduceLatLngPoints(final List<LatLng> latLngs) {
    	final int threshhold = 50;
    	final int size = latLngs.size();
    	log.info("latLng size : " + latLngs.size());
    	if(threshhold>size) {
    		return latLngs;
    	}
        final List<LatLng> reducedPoints = new ArrayList<>();
        final int elementsSkipSize = size / threshhold;

        int i=0;
        while(i * elementsSkipSize<size) {
            reducedPoints.add(latLngs.get(i * elementsSkipSize));
            i++;
        }
        return reducedPoints;
    }


	private LatLng getGeocodedLatLngFromAddress(final String location)
			throws ApiException, InterruptedException, IOException {
		log.info("getGeocodedLatLngFromAddress: Location:" + location);
        final long startms = System.currentTimeMillis();
        final GeocodingApiRequest endCoordsRequest = GeocodingApi.geocode(getContext(), location);

		final GeocodingResult[] response = endCoordsRequest.await();
		if (response == null || response.length < 1) {
			throw new RuntimeException("Cant find the location :" + location);
		}
        log.info("##Time taken for getGeocodedLatLngFromAddress results API ::" + (System.currentTimeMillis() - startms));
		return response[0].geometry.location;
	}

	private String[] latLngToStringArray(final List<LatLng> origins) {
		final String[] originsInString = new String[origins.size()];
		for (int i = 0; i < origins.size(); i++) {
			originsInString[i] = origins.get(i).toString();
		}
		return originsInString;
	}

	public class SearchComparator implements Comparator<RideSearch> {
		@Override
		public int compare(final RideSearch o1, final RideSearch o2) {
			Date d1 = null;
			Date d2 = null;
			int result = 0;
			try {
				d1 = DateUtil.DATE_FORMATTER_DATE_AND_TIME.parse(o1.getRide().getDepartureDate());
				d2 = DateUtil.DATE_FORMATTER_DATE_AND_TIME.parse(o2.getRide().getDepartureDate());
				result = d1.compareTo(d2);
			} catch (ParseException | NullPointerException e) {
				log.error("Error parsing date in the Ride list comparator", e);
			}
			return result;
		}
	}
	
	private void notifyUserWithRideDetails(com.gslab.parivahan.dbmodel.Ride rideDBObject, User user,String Charges) {
		String startLocation = trimLocation(rideDBObject.getStartLocation());
		String endLocation = trimLocation(rideDBObject.getEndLocation());
		
		String message = String.format(MessageTemplates.OFFERRIDE, rideDBObject.getOfferCode(),startLocation,endLocation,DateUtil.ConvertDateToUserReadableFormat(rideDBObject.getDepartureDateTime()));
	    notificationService.sendSms(rideDBObject.getUser().getMobileNumber(), message);
	    File file = ics.write(rideDBObject.getUser().getEmail(), "Ride Scheduled from " + startLocation + " to " + endLocation,
				DateUtil.ConvertDateToInvitationFormat(rideDBObject.getDepartureDateTime()),
				DateUtil.ConvertDateToInvitationFormat(DateUtil.addHours(rideDBObject.getDepartureDateTime(), 1)));
		EmailMessageBody emailBody = generateEmailBody(rideDBObject.getUser().getEmail(), rideDBObject.getOfferCode(),
				null, rideDBObject.getUser().getName(), rideDBObject.getStartLocation(), rideDBObject.getEndLocation(),
				rideDBObject.getDepartureDateTime().toString(),Charges,
				"Ride offered from " + startLocation + " to " + endLocation, "email/offerRide",
				getByteArray(rideDBObject.getUser().getEmail()));
		notificationService.sendEmail(emailBody);
		file.delete();
	}
	
	private EmailMessageBody generateEmailBody(String emailId,Integer offerCode,Integer bookCode,String hostName,String startLocation,String endLocation,String dateAndTime,String charges,String subject,String templateName,byte[] inviteFile) {
		EmailMessageBody email = new EmailMessageBody();
		List<String> toList = new ArrayList<String>();
		toList.add(emailId);
		email.setTo(toList);
		if (null != offerCode)
			email.setOfferid(offerCode);
		if (null != bookCode)
			email.setBookingid(bookCode);
		email.setHostName(hostName);
		email.setTemplateName(templateName);
		email.setBody("Test");
		email.setSubject(subject);
		email.setStartLocation(startLocation);
		email.setEndLocation(endLocation);
		email.setDateAndTime(dateAndTime);
		email.setCost(charges);
		if(null!=inviteFile) {
			email.setInvite(inviteFile);
		}
		return email;
	}

	@Override
	public List<String> searchPlaces(String query, String lat, String lng) {
		if(StringUtils.isBlank(query)) {
			return Collections.emptyList();
		}
		QueryAutocompleteRequest autoCompleteRequest = PlacesApi.queryAutocomplete(context, query);
		autoCompleteRequest.radius(DEFAULT_SEARCH_RADIUS_METERS);
		if(StringUtils.isNotBlank(lat)&& StringUtils.isNotBlank(lng)) {
			autoCompleteRequest.location(new LatLng(Double.valueOf(lat),Double.valueOf(lat)));
		} else {
			autoCompleteRequest.location(new LatLng(18.556588,73.794111));
		}
		AutocompletePrediction[] result = null;	
		List<String> predictionResults=new ArrayList<String>();
		try {
			result = autoCompleteRequest.await();
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		if(result==null)
			return predictionResults;
		for (AutocompletePrediction prediction : result) {
			if(StringUtils.isNotBlank(prediction.placeId)) {
				if(prediction.description.contains(env.getProperty("parivahan.ride.search.default.location")))
					predictionResults.add(prediction.description);
			}
		}
		return predictionResults;
	}

	@Override
	public Ride getRideWithRequests(Integer offerCode,String userEmail) {
		com.gslab.parivahan.dbmodel.Ride ride=rideDao.getRideByRideOfferCode(offerCode);
		if(ride==null) {
			throw new RuntimeException("No rides found");
		}
		if(ride.getUser()!=null && StringUtils.isNotBlank(ride.getUser().getEmail())) {
			if(!userEmail.trim().equals(ride.getUser().getEmail())) {
				throw new RuntimeException("No Offer matches with the provided user information: "+userEmail);
			}
		}
		Ride rideVO=new Ride(ride);
		List<RideRequest> rideRequests=requestDao.getRideRequestByRideId(ride.getId());
		if(rideRequests!=null) {
			List<RideRequestVO> rideRequestsVO=new ArrayList<>();
			for (RideRequest rideRequest : rideRequests) {
				rideRequest.setRide(null);
				RideRequestVO request = new RideRequestVO(rideRequest);
				rideRequestsVO.add(request);
			}
			rideVO.setRideRequests(rideRequestsVO);
		} else {
			rideVO.setRideRequests(new ArrayList<RideRequestVO>());
		}
		
		return rideVO;
	}
	
	private void sendNotificationOnRideCancelToHost(com.gslab.parivahan.dbmodel.Ride ride) {
		String startLocation = trimLocation(ride.getStartLocation());
		String endLocation = trimLocation(ride.getEndLocation());

		String messageForHost = String.format(MessageTemplates.CANCELRIDEFORHOST, ride.getOfferCode(),startLocation,endLocation,DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()));
		notificationService.sendSms(ride.getUser().getMobileNumber(), messageForHost);
		
		EmailMessageBody emailBodyForHost = generateEmailBody(ride.getUser().getEmail(), ride.getOfferCode(), null,
				ride.getUser().getName(), ride.getStartLocation(), ride.getEndLocation(),
				ride.getDepartureDateTime().toString(), ride.getCharges().toString(),
				"Ride Cancelled from From " + startLocation + " to " + endLocation, "email/cancelRideForHost",null);
		notificationService.sendEmail(emailBodyForHost);
	}

	private void sendNotificationOnRideCancelToRider(RideRequest rideRequest, com.gslab.parivahan.dbmodel.Ride ride) {
		String startLocation = trimLocation(rideRequest.getStartLocation());
		String endLocation = trimLocation(rideRequest.getEndLocation());
		
		String messageForRider = String.format(MessageTemplates.CANCELRIDEFORRIDER, rideRequest.getBookingCode(),startLocation,endLocation,DateUtil.ConvertDateToUserReadableFormat(ride.getDepartureDateTime()));
		notificationService.sendSms(rideRequest.getUser().getMobileNumber(), messageForRider);
		
		EmailMessageBody emailBodyforRider = generateEmailBody(rideRequest.getUser().getEmail(), null,
				rideRequest.getBookingCode(), rideRequest.getUser().getName(), rideRequest.getStartLocation(),
				rideRequest.getEndLocation(), ride.getDepartureDateTime().toString(), null,
				"Ride Cancelled from From " + startLocation + " to " + endLocation, "email/cancelRideForRider",null);
		notificationService.sendEmail(emailBodyforRider);
	}

	@Override
	public List<DirectionPaths> getDirectionDetailsWithAlternatives(String startLocation, String endLocation) {
		DirectionsResult response = this.getDirections(startLocation, endLocation, false, true);
		if(response==null) {
			throw new RuntimeException("Directions not found for input location");
		}
		List<DirectionPaths> directionPaths=new ArrayList<>();
		DirectionsRoute[] routes = response.routes;
		for(int i=0;i< routes.length;i++) {
			directionPaths.add(new DirectionPaths(routes[i].summary,routes[i].overviewPolyline.decodePath()));
		}
		return directionPaths;
	}
	
	@Override
	public List<Ride> getRecentUserRides(String emailId){
		List<com.gslab.parivahan.dbmodel.Ride> rides = rideDao.getRecentRides(emailId);
		List<Ride> ridesVO = new ArrayList<>();
		for (com.gslab.parivahan.dbmodel.Ride ride : rides) {
			Ride recentRide = new Ride(ride);
			if (recentRide.getVehicle().toString().equalsIgnoreCase(Vehicle.BIKE.toString())) {
				recentRide.setCharges((MINIMUM_RIDE_CHARGE>recentRide.getCharges()/2)?MINIMUM_RIDE_CHARGE:recentRide.getCharges()/2);
			} else if (recentRide.getVehicle().toString().equalsIgnoreCase(Vehicle.CAR.toString())) {
				recentRide.setCharges((MINIMUM_RIDE_CHARGE>recentRide.getCharges()/4)?MINIMUM_RIDE_CHARGE:recentRide.getCharges()/4);
			}
			ridesVO.add(recentRide);
		}
		return ridesVO;
	}
	
	@Override
	public List<Ride> getShuttleByDate(String date){
		List<com.gslab.parivahan.dbmodel.Ride> rides = rideDao.getShuttleByDate( DateUtil.parseDate(DateUtil.DATE_FORMATTER_DATE_AND_TIME,date));
		List<Ride> shuttleResult = new ArrayList<Ride>();
		for (com.gslab.parivahan.dbmodel.Ride ride : rides) {
			shuttleResult.add(new Ride(ride));
		}
		return shuttleResult;
	}
	
	@Override
	public int countTotalRides() {
		return rideDao.countTotalRides();
	}
	
	private String trimLocation(String location) {
		if (location.length() < 10) {
			return location;
		} else {
			return location.substring(0, 10)+"...";
		}
	}
	
	private byte[] getByteArray(String email) {
		String filePath = env.getProperty("parivahan.support.email.invitation.path")+email+".ics";
		File file = new File(filePath);
		byte[] bytesArray = new byte[(int) file.length()]; 
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			fis.read(bytesArray);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytesArray;
	}

	@Override
	public ShuttleScheduleVO addShuttleSchedule(ShuttleScheduleVO shuttleScheduleVO) {
		if(shuttleScheduleVO==null) {
			return null;
		}
		for (String  schedule : shuttleScheduleVO.getSchedules()) {
			Ride ride=new Ride(shuttleScheduleVO.getStartLocation(),shuttleScheduleVO.getEndLocation(),shuttleScheduleVO.getTotalSeats());
			ride.setAvailableSeats(shuttleScheduleVO.getTotalSeats());
			ride.setDepartureDate(schedule);
			ride.setUserVO(shuttleScheduleVO.getUser());
			ride.setVehicle(Vehicle.SHUTTLE);
			try {
				this.addUserRide(ride, false);
			} catch (Exception e) {
				log.error("Failed to add schedule for departure date : "+schedule);
			}
		}
		return shuttleScheduleVO;
		
	}
}
