package com.jmsc.app.config.jmsc;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@ConfigurationProperties(prefix = "jmsc.general.conf")
@EqualsAndHashCode(callSuper = false)
@Component
@Data
@Slf4j
public class JmscGeneralConfig {
	
	
	private String banks;
	
	private String states;
	
	private String einvoiceStartYear;
	
	
	@PostConstruct
	 private void init() {
		log.debug("Activated: {}",this.getClass());
	 }
	
	@Override
	public String toString() {
		return String.format(
				"PrimaryDataSourceConfig [Banks =%s]", banks);
	}

}
