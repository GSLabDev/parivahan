package com.gslab.parivahan.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gslab.parivahan.model.DirectionPaths;
import com.gslab.parivahan.service.IDirectionsService;
import com.gslab.parivahan.util.RideConstants;

@Controller
@RequestMapping("/api")
public class MapDisplayController {

	@Autowired
	IDirectionsService directionService;

	@RequestMapping(value = RideConstants.VERSION_1+"/map", method = RequestMethod.GET)
	public String displayMap(Model model, @RequestParam String startLocation, @RequestParam String endLocation) {
		model.addAttribute("startLocation", startLocation);
		model.addAttribute("endLocation", endLocation);
		return "maps";

	}

	@RequestMapping(value = RideConstants.VERSION_1+"/direction", method = RequestMethod.GET)
	@ResponseBody
	public List<DirectionPaths> getDIrectionDetails(@RequestParam String startLocation,
			@RequestParam String endLocation) {
		List<DirectionPaths> directionPaths = directionService.getDirectionDetailsWithAlternatives(startLocation,
				endLocation);
		return directionPaths;
	}

}
