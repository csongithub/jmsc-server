/**
 * 
 */
package com.jmsc.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.jmsc.app.config.jmsc.JmscEvent;
import com.jmsc.app.config.jmsc.JmscEventPublisher;
import com.jmsc.app.config.jmsc.ServiceLocator;



/**
 * @author chandan
 *
 */


@SpringBootApplication
@ComponentScan ({"com.jmsc.app", "com.jmsc.app.service", "com.jmsc.app.repository", "com.jmsc.app.persistence"})
@EnableAutoConfiguration
@EnableConfigurationProperties
public class JMSCApplication {
	
	public static void main(String[] args) {
		System.out.println("Java Version- " + System.getProperty("java.version"));
		System.setProperty("spring.devtools.restart.enabled", "false");
		
		ConfigurableApplicationContext ctx = SpringApplication.run(JMSCApplication.class, args);
		
		ServiceLocator.getInstance().initialize(ctx);
		
		/**
		 * Call hook to execute post activation calls
		 */
		executePostActivte();
	}
	
	
	/**
	 * This is a hook method to call all services which needs to start once post activation of this application
	 */
	private static void executePostActivte() {

		System.out.println("Executing post activation....");
		JmscEventPublisher eventPublisher = ServiceLocator.getService(JmscEventPublisher.class);
		
		Map<String, Object> eventMap = new HashMap<String, Object>();
		eventMap.put("topic", JmscEvent.POST_ACTIVATE_EVENT_TOPIC);
		eventMap.put("payload", null);
		
		eventPublisher.publishEvent(eventMap);
		
		
	}
	
}
