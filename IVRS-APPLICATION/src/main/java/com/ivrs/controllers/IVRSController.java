package com.ivrs.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class IVRSController {

	@RequestMapping(value = "/validatePin")
	@ResponseBody
	public ResponseEntity<Object> validatePin(@RequestParam("pin") String pin) {

		pin = pin.trim();
		if (!StringUtils.isEmpty(pin) && pin.equalsIgnoreCase("125")) {
			System.out.println("pin: " + pin);
			System.out.println("------------comming is success block");
			return ResponseEntity.status(HttpStatus.OK).body("success");

		} else {
			System.out.println("failure block pin: " + pin + "a");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

	}

	@RequestMapping(value = "/getAgent", method = RequestMethod.GET )
	@ResponseBody
	public ResponseEntity<Object> getAgent(@RequestParam("type") String agentType) {
		 
		agentType = agentType.trim();
		System.out.println("agent type ::===" + agentType);
		if (!StringUtils.isEmpty(agentType) && (agentType.equalsIgnoreCase("1") || agentType.equalsIgnoreCase("2")
				|| agentType.equalsIgnoreCase("3") || agentType.equalsIgnoreCase("4") || agentType.equalsIgnoreCase("5")
				|| agentType.equalsIgnoreCase("6"))) {
			System.out.println("agent type ::===" + agentType);

			String number = null;
			switch (Integer.parseInt(agentType)) {

			case 1:
				number = getControllerAgent();
				break;
			case 2:
				number = getNetAgent();
				break;

			case 3:
				number = getJavaAgent();
			}

			String jsonStr = null;
			Map<String, String> numberMap = new HashMap<String, String>();
			numberMap.put("number", number);
			Gson gsonObj = new Gson();
			jsonStr = gsonObj.toJson(numberMap);
			System.out.println("Returning json object" + jsonStr);
			return new ResponseEntity<Object>(jsonStr, HttpStatus.OK);
			

		} else {
			System.out.println("Bad request");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

	}

	private String getControllerAgent() {
		//Rajendra's number
		String number = "+911223444";
		return number;

	}

	private String getJavaAgent() {
		//Har
		String number = "+911234567";
		return number;

	}

	private String getNetAgent() {
		//Saradhi's number
		String number = "+911223444";
		return number;

	}

}