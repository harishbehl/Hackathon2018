package com.ivrs.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.FileReader;

import org.springframework.http.HttpStatus;
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

	static final String PROPERTIES_FILE_NAME = "ivrs.properties";
	static final String CONTROLLER_PHONE = "controller";
	static final String JAVA_PHONE = "java";
	static final String DOTNET_PHONE = "dotnet";
	static final String PIN_NUMBER = "pinnumber";
	static final String SALES_PHONE = "sales";
	static final String LICENSE_PHONE = "license";
	static final String OTHER_PHONE = "other";

	@RequestMapping(value = "/validatePin")
	@ResponseBody
	public ResponseEntity<Object> validatePin(@RequestParam("pin") String pin) throws Exception {

		pin = pin.trim();
		if (!StringUtils.isEmpty(pin) && pin.equalsIgnoreCase(getPropertyValue(PIN_NUMBER))) {
			System.out.println("pin: " + pin);
			System.out.println("------------coming in success block");
			return ResponseEntity.status(HttpStatus.OK).body("success");

		} else {
			System.out.println("failure block pin: " + pin + "a");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

	}

	@RequestMapping(value = "/getAgent", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getAgent(@RequestParam("type") String agentType) {
		agentType = agentType.trim();
		System.out.println("agent type ::===" + agentType);
		if (!StringUtils.isEmpty(agentType) && (agentType.equalsIgnoreCase("1") || agentType.equalsIgnoreCase("2")
				|| agentType.equalsIgnoreCase("3") || agentType.equalsIgnoreCase("4") || agentType.equalsIgnoreCase("5")
				|| agentType.equalsIgnoreCase("6"))) {
			System.out.println("agent type ::===" + agentType);

			String number = null;

			try {
				switch (Integer.parseInt(agentType)) {

				case 1:
					number = getControllerAgent();
					break;
				case 2:
					number = getJavaAgent();
					break;

				case 3:
					number = getNetAgent();
				
				case 4:
					number = getLicenseAgent();
				
				case 5:
					number = getOther();
				}
			} catch (Exception e) {
				e.printStackTrace();
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

	static String getPropertyValue(String propertyName) throws IOException {
		FileReader reader = null;
		String propertyValue = null;
		try {
			reader = new FileReader(PROPERTIES_FILE_NAME);
			Properties p = new Properties();
			p.load(reader);
			propertyValue = p.getProperty(propertyName);
		} catch (IOException ie) {
			ie.printStackTrace();
		} finally {
			if (reader != null)
				reader.close();
		}
		return propertyValue;

	}

	private String getControllerAgent() throws IOException {
		return getPropertyValue(CONTROLLER_PHONE);

	}

	private String getJavaAgent() throws IOException {
		return getPropertyValue(JAVA_PHONE);

	}

	private String getNetAgent() throws IOException {
		return getPropertyValue(DOTNET_PHONE);

	}

	@RequestMapping(value = "/getSalesAgent", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Object> getSalesAgent(@RequestParam("agentSales") String agentSales) throws IOException {
		agentSales = agentSales.trim();
		System.out.println("agent type ::===" + agentSales);
		if (!StringUtils.isEmpty(agentSales) && agentSales.equalsIgnoreCase("1")) {
			System.out.println("agent type ::===" + agentSales);
			String jsonStr = null;
			String number = getSalesNumber();
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

	private String getSalesNumber() throws IOException {
		return getPropertyValue(SALES_PHONE);
	}
	
	private String getLicenseAgent() throws IOException {
		return getPropertyValue(LICENSE_PHONE);
	}
	
	private String getOther() throws IOException {
		return getPropertyValue(OTHER_PHONE);
	}

}