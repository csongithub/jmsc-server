package com.jmsc.app.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/v1/app")
@Api(value = "Application Status")
public class AppEndPoint {
	
	
	@GetMapping("/status")
	public  String status(){
		return "JMSC server is running";
	}
}
