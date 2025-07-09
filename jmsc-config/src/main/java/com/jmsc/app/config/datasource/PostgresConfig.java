/**
 * 
 */
package com.jmsc.app.config.datasource;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Chandan
 *
 */
@ConfigurationProperties(prefix = "jmsc.service.postgres")
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@Slf4j
public class PostgresConfig extends DataSourceConfig {
	
	private boolean backupEnabled;
	
	private String pgDumpAgent;
	
	private String outputDirectory;
	
	private String format;
	
	private String host;
	
	private String port;
	
	private String database;
	
	private String username;
	
	private String password;	
	
	
	@PostConstruct
	 private void init() {
		log.debug("Activated: {}",this.getClass());
	 }
}
