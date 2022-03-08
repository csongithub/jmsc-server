package com.jmsc.app.config.jmsc;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

@ConfigurationProperties(prefix = "jmsc.properties")
@EqualsAndHashCode(callSuper = false)
@Component
@Data
@Slf4j
public class JmscProperties{
	
	private String banksName;
	
	private String banksBranch;
	
	private String postOfficeBranch;
	
	private String cfExpiryAlertDays;
	
	
	@PostConstruct
	 private void init() {
		log.debug("Activated: {}",this.getClass());
	 }

	@Override
	public String toString() {
		return String.format(
				"PrimaryDataSourceConfig [Banks Name=%s, Banks Branch=%s, Post Office=%s]", banksName, banksBranch, postOfficeBranch);
	}
	
	public Long getCreditFacilityExpiryAlertDays() {
		return Long.parseLong(cfExpiryAlertDays);
	}
}
