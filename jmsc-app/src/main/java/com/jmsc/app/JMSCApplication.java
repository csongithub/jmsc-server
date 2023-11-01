/**
 * 
 */
package com.jmsc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;



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
	}
}
