package com.jmsc.app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/app")
public class AppEndPoint {
	
	
	@GetMapping("/status")
	public  String status(){
		return "JMSC server is running";
	}
}
