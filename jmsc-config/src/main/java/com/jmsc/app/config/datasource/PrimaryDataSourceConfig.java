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
 * @author chandan
 *
 */
@ConfigurationProperties(prefix = "spring.datasource.primary")
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@Slf4j
public class PrimaryDataSourceConfig extends DataSourceConfig {

	String jdbcUrl;

	String username;

	String password;

	String connectionTestQuery;
	
	String connectionInitSql;
	
	
	
	@PostConstruct
	 private void init() {
		log.debug("Activated: {}",this.getClass());
	 }

	@Override
	public String toString() {
		return String.format(
				"PrimaryDataSourceConfig [jdbcUrl=%s, username=%s, password=<masked>, cachePrepStmts=%s, prepStmtCacheSize=%s, prepStmtCacheSqlLimit=%s, poolName=%s, maximuPoolSize=%s, connectionTestQuery=%s, connectionInitSql=%s]",
				jdbcUrl, username, cachePrepStmts, prepStmtCacheSize,prepStmtCacheSqlLimit, poolName, maximumPoolSize, connectionTestQuery,connectionInitSql);
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConnectionTestQuery() {
		return connectionTestQuery;
	}

	public void setConnectionTestQuery(String connectionTestQuery) {
		this.connectionTestQuery = connectionTestQuery;
	}

	
	
}