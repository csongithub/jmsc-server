package com.jmsc.app.service.email;

import lombok.Data;

@Data
public class Envelop {
	
	private String recipients;
	
	private String subject;
	
	private Body body;
}
