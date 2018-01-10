package com.ivrs.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Body.Builder;
import com.twilio.twiml.messaging.Message;

@RestController
public class IVRSController {

	@RequestMapping(value = "/validatePin")
	@ResponseBody
	public ResponseEntity<Object> validatePin(@RequestParam("pin") String pin) {
		MessagingResponse response1 = null;
		pin=pin.trim();
		if (!StringUtils.isEmpty(pin) && pin.equalsIgnoreCase("125") ){
			System.out.println("pin: " + pin);
			Builder builder = new Body.Builder("Success");
			Body body = builder.build();
			Message message = new Message.Builder().body(body).build();
			response1 = new MessagingResponse.Builder().message(message).build();
			System.out.println("------------comming is success block");
			return ResponseEntity.status(HttpStatus.OK).body("success");
			
		} else {
			Builder builder = new Body.Builder("Fail");
			System.out.println("failure block pin: " + pin+"a");
			Body body = builder.build();
			Message message = new Message.Builder().body(body).build();
			response1 = new MessagingResponse.Builder().message(message).build();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
	}
	
	@RequestMapping(value = "/getAgent")
	@ResponseBody
	public ResponseEntity<Object> getAgent(@RequestParam("agentType") String agentType) {
		agentType=agentType.trim();
		if (!StringUtils.isEmpty(agentType) && (agentType.equalsIgnoreCase("1") ||agentType.equalsIgnoreCase("2") ||
				agentType.equalsIgnoreCase("3") || agentType.equalsIgnoreCase("4") || agentType.equalsIgnoreCase("5")
				|| agentType.equalsIgnoreCase("6"))){
			return ResponseEntity.status(HttpStatus.OK).body("success");
		}else {
			Builder builder = new Body.Builder("Fail");
			System.out.println("failure block pin: " + agentType+"a");
			Body body = builder.build();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
	}
	
}