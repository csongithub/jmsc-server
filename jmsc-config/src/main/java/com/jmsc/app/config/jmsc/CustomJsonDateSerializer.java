/**
 * 
 */
package com.jmsc.app.config.jmsc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author Chandan
 *
 */
public class CustomJsonDateSerializer extends JsonSerializer<Date> {

	private final String DATE_FORMAT = "dd-MM-yyyy";

	@Override
	public void serialize(Date date, 
						  JsonGenerator gen,
						  SerializerProvider serializers) throws IOException {
		 SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		    String dateString = dateFormat.format(date);
		    gen.writeString(dateString);
		
	}

}
