/**
 * 
 */
package com.jmsc.app.config.jmsc;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author Chandan
 *
 */
@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

	@Override
	  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	    JavaTimeModule module = new JavaTimeModule();
	    module.addDeserializer(Date.class, new CustomJsonDateDeserializer());

	    ObjectMapper mapper = new ObjectMapper();
	    mapper.registerModule(module);

	    // Add converter at the first one to use
	    converters.add(0, new MappingJackson2HttpMessageConverter(mapper));
	  }
}
