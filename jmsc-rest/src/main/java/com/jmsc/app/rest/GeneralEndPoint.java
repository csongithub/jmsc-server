/**
 * 
 */
package com.jmsc.app.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
			case "BANKS":
				String allBanks = config.getBanks();
				list = allBanks.split(",");
				break;
			case "GST":
				String allStates = config.getGst();
				list = allStates.split(",");
				break;
			case "EINVOICE_YEARS":
				String einvoiceStartYear = config.getEinvoiceStartYear();
				List<String> listStr = getFyForEInvoice(einvoiceStartYear);
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
	
	
	private List<String> getFyForEInvoice(String einvoiceStartYear){

		String currentFy = this.getCurrentFinancialYear();
		
    	String[] lit = currentFy.split("-");
    	int start = Integer.parseInt(lit[0]);
    	int end = Integer.parseInt(lit[1]);
    	
    	List<String> fyList = new ArrayList<String>();
    	
    	String nextFy = currentFy;
    	while(!nextFy.equals(einvoiceStartYear)) {
    		fyList.add(nextFy);
    		--start;
    		--end;
    		if(end == 0) {
    			nextFy =  start + "-" + "00";
    			end = start %100 + 1;
    		}else if(end%10 == end) {
    			nextFy =  start + "-" + "0"+end;
    		} else {
    			nextFy =  start + "-" + end;
    		}
    		
    	}
    	fyList.add(einvoiceStartYear);
    	return fyList;
	}
	
	private String getCurrentFinancialYear(){
		Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int fyStart =  (month >= Calendar.MARCH) ? year : year - 1;
        String fy = fyStart + "-" + (fyStart%100 + 1);
        return fy;
	}
}
