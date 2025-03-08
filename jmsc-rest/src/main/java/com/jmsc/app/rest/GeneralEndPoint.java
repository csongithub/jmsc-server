/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.config.jmsc.JmscGeneralConfig;
import com.jmsc.app.service.UtilityService;

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
			case "BANK":
				String allBanks = config.getBanks();
				list = allBanks.split(",");
				break;
			case "GST":
				String allStates = config.getGst();
				list = allStates.split(",");
				break;
			case "EINVOICE_YEARS":
				String einvoiceStartYear = config.getEinvoiceStartYear();
				List<String> listStr = UtilityService.getFyForEInvoice(einvoiceStartYear);
				list = new String[listStr.size()];
				listStr.toArray(list);
				break;
			case "PROJECTS":
				String projects = config.getProjects();
				list = projects.split(",");
				break;
			case "DIVISIONS":
				String divisions = config.getDivisions();
				list = divisions.split(",");
				break;
		}
		return ResponseEntity.ok(list);
	}
	
	

}
