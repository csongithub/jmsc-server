/**
 * 
 */
package com.jmsc.app.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Slf4j
@RestController
@RequestMapping("/v1/enums")
@Api(value = "Application Status")
public class EnumerationsEndPoint {
	
	@GetMapping("/get_all_options/{enumClassName}")
	public List<String> getEnums(@PathVariable("enumClassName") String enumClassName) throws ClassNotFoundException {
		String qualifiedEnumClass = "com.jmsc.app.common.enums." + enumClassName;
		log.debug("Getting enum for {}", qualifiedEnumClass);
		@SuppressWarnings("rawtypes")
		Class clazz = Class.forName(qualifiedEnumClass);
		List<String> options = new ArrayList<String>();
		
		for(Field f : clazz.getDeclaredFields()) {
			if(f.getName().contains("$"))
				continue;
			
			options.add(f.getName());
		}
		return options;
	}

}
