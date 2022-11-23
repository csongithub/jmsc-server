/**
 * 
 */
package com.jmsc.app.service;

import java.util.Base64;

import org.springframework.stereotype.Service;

/**
 * @author chandan
 *
 */
@Service
public class Base64Service {
	
	public static String encode(byte[] bytes) {
		String encodedString = Base64.getEncoder().encodeToString(bytes);
		return encodedString;
	}
	
	
	
	public static String encode(String message) {
		return encode(message.getBytes());
	}
	
	
	
	public static String decode(String message) {
		byte[] decodedBytes = Base64.getDecoder().decode(message);
		String decodedString = new String(decodedBytes);
		return decodedString;
	}
}
