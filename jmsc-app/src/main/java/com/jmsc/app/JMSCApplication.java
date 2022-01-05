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



/**
 * @author chandan
 *
 */

//@EnableEurekaClient
//@EnableFeignClients("com.workly.common.feignclient")
@SpringBootApplication
@ComponentScan ("com.jmsc.app")
//@EnableJpaRepositories(basePackages = { "com.jmsc.app.repository" })
@EnableAutoConfiguration
@EnableConfigurationProperties
public class JMSCApplication {
	public static void main(String[] args) {
		System.out.println("Java Version- " + System.getProperty("java.version"));
		SpringApplication.run(JMSCApplication.class, args);
	}
}
