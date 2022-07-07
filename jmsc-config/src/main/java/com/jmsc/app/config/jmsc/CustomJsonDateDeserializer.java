/**
 * 
 */
package com.jmsc.app.config.jmsc;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@Slf4j
public class CustomJsonDateDeserializer extends JsonDeserializer<Date>{
	
	private final String DATE_FORMAT = "dd-MM-yyyy";
	

	
	@Override
	  public Date deserialize(JsonParser jsonParser,
	                          DeserializationContext deserializationContext)
	                             throws IOException, JsonProcessingException {

	    SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
	    String date = jsonParser.getText();
	    
	    if(isValidFormat(date)) {
	    	try {
		        return format.parse(date);
		    } catch (ParseException e) {
		        throw new RuntimeException(e);
		    }
	    } else {
	    	log.error("Invalid Date Format, Supported only " + DATE_FORMAT);
	    	return null;
//	    	throw new RuntimeException("Invalid Date Format, Supported only " + DATE_FORMAT);
	    }
	  
	  }
	
	public boolean isValidFormat(String date) {
        DateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

}
