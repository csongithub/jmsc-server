/**
 * 
 */
package com.jmsc.app.service.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

/**
 * @author Chandan
 *
 */
@Service
public class JMSCResource {
	
	
	public String readAsString(String r, VelocityContext context) {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(r);
		try {
			InputStream inputStream = resource.getInputStream();
	        byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
	        String templateAsString = new String(bdata, StandardCharsets.UTF_8);
	        
	        StringWriter writer = new StringWriter();
	        if(context != null) {
	        	Velocity.evaluate(context, writer, r , templateAsString);
		        return writer.toString();
	        }else {
	        	return templateAsString ;
	        }
	        	
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		return null;
	}
}
