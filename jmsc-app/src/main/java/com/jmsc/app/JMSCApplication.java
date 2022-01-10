/**
 * 
 */
package com.jmsc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



/**
 * @author chandan
 *
 */


@SpringBootApplication
@ComponentScan ({"com.jmsc.app", "com.jmsc.app.service", "com.jmsc.app.repository", "com.jmsc.app.persistence"})
@EnableAutoConfiguration
@EnableConfigurationProperties
// @EnableJpaRepositories("com.jmsc.app.repository")
public class JMSCApplication {
	public static void main(String[] args) {
		System.out.println("Java Version- " + System.getProperty("java.version"));
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(JMSCApplication.class, args);
	}
}
