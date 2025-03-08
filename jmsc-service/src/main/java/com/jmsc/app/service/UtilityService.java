/**
 * 
 */
package com.jmsc.app.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author anuhr
 *
 */
@Service
public class UtilityService {
	
	public static String getCurrencyFormat(double value) {
	    if(value < 1000) {
	        return format("###", value);
	    } else {
	        double hundreds = value % 1000;
	        int other = (int) (value / 1000);
	        return format(",##", other) + ',' + format("000", hundreds);
	    }
	}

	private static String format(String pattern, Object value) {
	    return new DecimalFormat(pattern).format(value);
	}
	
	
	public static List<String> getFyForEInvoice(String einvoiceStartYear){

		String currentFy = getCurrentFinancialYear();
		
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
	
	public static String getCurrentFinancialYear(){
		Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int fyStart =  (month > Calendar.MARCH) ? year : year - 1;
        String fy = fyStart + "-" + (fyStart%100 + 1);
        return fy;
	}

}
