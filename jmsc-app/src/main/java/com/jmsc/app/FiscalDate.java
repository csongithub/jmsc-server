package com.jmsc.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FiscalDate {

    private static final int    FIRST_FISCAL_MONTH  = Calendar.MARCH;

   




    public String getFiscalYear() {
    	Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int fyStart =  (month >= FIRST_FISCAL_MONTH) ? year : year - 1;
        String fy = fyStart + "-" + (fyStart%100 + 1);
        return fy;
    }

  

    public static void main(String[] args) {
    	
    	String fyStart = "2020-21";
    	
    	FiscalDate ob = new FiscalDate();
    	String currentFy = ob.getFiscalYear();
    	
    	String[] lit = currentFy.split("-");
    	int start = Integer.parseInt(lit[0]);
    	int end = Integer.parseInt(lit[1]);
    	
    	List<String> fyList = new ArrayList<String>();
    	
    	String nextFy = currentFy;
    	while(!nextFy.equals(fyStart)) {
    		fyList.add(nextFy);
    		nextFy = (--start) + "-" + (--end);
    	}
    	
    	System.out.println(fyList);
    }
    
   

}