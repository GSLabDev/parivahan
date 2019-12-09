/*
 * Copyright (c) 2003-2019, Great Software Laboratory Pvt. Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gslab.parivahan.util;

import static com.gslab.parivahan.util.MathUtil.*;
import static java.lang.Math.*;

import java.util.List;

import com.google.maps.model.LatLng;
public class PolyUtil {
	public static int locationIndexOnEdgeOrPath(LatLng point, List<LatLng> latlngs, boolean closed, boolean geodesic,
			double toleranceEarth) {
		int size = latlngs.size();
		if (size == 0) {
			return -1;
		}
		double tolerance = toleranceEarth / EARTH_RADIUS;
		double havTolerance = hav(tolerance);
		double lat3 = toRadians(point.lat);
		double lng3 = toRadians(point.lng);
		com.google.maps.model.LatLng prev = latlngs.get(closed ? size - 1 : 0);
		double lat1 = toRadians(prev.lat);
		double lng1 = toRadians(prev.lng);
		int idx = 0;
		if (geodesic) {
			for (LatLng point2 : latlngs) {
				double lat2 = toRadians(point2.lat);
				double lng2 = toRadians(point2.lng);
				if (isOnSegmentGC(lat1, lng1, lat2, lng2, lat3, lng3, havTolerance)) {
					return Math.max(0, idx - 1);
				}
				lat1 = lat2;
				lng1 = lng2;
				idx++;
			}
		} else {
			double minAcceptable = lat3 - tolerance;
			double maxAcceptable = lat3 + tolerance;
			double y1 = mercator(lat1);
			double y3 = mercator(lat3);
			double[] xTry = new double[3];
			for (LatLng point2 : latlngs) {
				double lat2 = toRadians(point2.lat);
				double y2 = mercator(lat2);
				double lng2 = toRadians(point2.lng);
				if (max(lat1, lat2) >= minAcceptable && min(lat1, lat2) <= maxAcceptable) {
					double x2 = wrap(lng2 - lng1, -PI, PI);
					double x3Base = wrap(lng3 - lng1, -PI, PI);
					xTry[0] = x3Base;
					xTry[1] = x3Base + 2 * PI;
					xTry[2] = x3Base - 2 * PI;
					for (double x3 : xTry) {
						double dy = y2 - y1;
						double len2 = x2 * x2 + dy * dy;
						double t = len2 <= 0 ? 0 : clamp((x3 * x2 + (y3 - y1) * dy) / len2, 0, 1);
						double xClosest = t * x2;
						double yClosest = y1 + t * dy;
						double latClosest = inverseMercator(yClosest);
						double havDist = havDistance(lat3, latClosest, x3 - xClosest);
						if (havDist < havTolerance) {
							return Math.max(0, idx - 1);
						}
					}
				}
				lat1 = lat2;
				lng1 = lng2;
				y1 = y2;
				idx++;
			}
		}
		return -1;
	}

	 public static boolean isLocationOnEdge(LatLng point, List<com.google.maps.model.LatLng> latlngs, boolean geodesic,
                                           double tolerance) {
        return isLocationOnEdgeOrPath(point, latlngs, true, geodesic, tolerance);
    }

	private static boolean isLocationOnEdgeOrPath(LatLng point, List<com.google.maps.model.LatLng> latlngs, boolean closed, boolean geodesic,
			double toleranceEarth) {
		int idx = locationIndexOnEdgeOrPath(point, latlngs, closed, geodesic, toleranceEarth);

		return (idx >= 0);
	}
	    private static boolean isOnSegmentGC(double lat1, double lng1, double lat2, double lng2,
                                         double lat3, double lng3, double havTolerance) {
        double havDist13 = havDistance(lat1, lat3, lng1 - lng3);
        if (havDist13 <= havTolerance) {
            return true;
        }
        double havDist23 = havDistance(lat2, lat3, lng2 - lng3);
        if (havDist23 <= havTolerance) {
            return true;
        }
        double sinBearing = sinDeltaBearing(lat1, lng1, lat2, lng2, lat3, lng3);
        double sinDist13 = sinFromHav(havDist13);
        double havCrossTrack = havFromSin(sinDist13 * sinBearing);
        if (havCrossTrack > havTolerance) {
            return false;
        }
        double havDist12 = havDistance(lat1, lat2, lng1 - lng2);
        double term = havDist12 + havCrossTrack * (1 - 2 * havDist12);
        if (havDist13 > term || havDist23 > term) {
            return false;
        }
        if (havDist12 < 0.74) {
            return true;
        }
        double cosCrossTrack = 1 - 2 * havCrossTrack;
        double havAlongTrack13 = (havDist13 - havCrossTrack) / cosCrossTrack;
        double havAlongTrack23 = (havDist23 - havCrossTrack) / cosCrossTrack;
        double sinSumAlongTrack = sinSumFromHav(havAlongTrack13, havAlongTrack23);
        return sinSumAlongTrack > 0;  // Compare with half-circle == PI using sign of sin().
    }
	 private static double sinDeltaBearing(double lat1, double lng1, double lat2, double lng2,
                                          double lat3, double lng3) {
        double sinLat1 = sin(lat1);
        double cosLat2 = cos(lat2);
        double cosLat3 = cos(lat3);
        double lat31 = lat3 - lat1;
        double lng31 = lng3 - lng1;
        double lat21 = lat2 - lat1;
        double lng21 = lng2 - lng1;
        double a = sin(lng31) * cosLat3;
        double c = sin(lng21) * cosLat2;
        double b = sin(lat31) + 2 * sinLat1 * cosLat3 * hav(lng31);
        double d = sin(lat21) + 2 * sinLat1 * cosLat2 * hav(lng21);
        double denom = (a * a + b * b) * (c * c + d * d);
        return denom <= 0 ? 1 : (a * d - b * c) / sqrt(denom);
    }
}
