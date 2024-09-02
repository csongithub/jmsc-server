/**
 * 
 */
package com.jmsc.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.config.jmsc.JmscGeneralConfig;

import io.swagger.annotations.Api;

/**
 * @author anuhr
 *
 */
@RestController
@RequestMapping("/v1/general")
@Api(value = "APIs to loans")
public class GeneralEndPoint {
	
	
	@Autowired
	private JmscGeneralConfig config;
	
	
	@GetMapping("entry/{name}")
	public ResponseEntity<String[]> getEntries(@PathVariable("name") String entryName){
		String[] list = null;
		switch(entryName) {
			case "Bank":
				String allBanks = config.getBanks();
				list = allBanks.split(",");
			break;
		}
		return ResponseEntity.ok(list);
	}
}
