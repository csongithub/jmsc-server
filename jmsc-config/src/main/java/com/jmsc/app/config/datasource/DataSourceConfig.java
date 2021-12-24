/**
 * 
 */
package com.jmsc.app.config.datasource;

import lombok.Data;

/**
 * @author chandan
 *
 */
@Data
public class DataSourceConfig {

	String cachePrepStmts = "true";

	String prepStmtCacheSize = "250";

	String prepStmtCacheSqlLimit = "2048";

	String poolName;

	Integer maximumPoolSize = 5;

	public DataSourceConfig() {
		super();
	}

	public String getCachePrepStmts() {
		return cachePrepStmts;
	}

	public void setCachePrepStmts(String cachePrepStmts) {
		this.cachePrepStmts = cachePrepStmts;
	}

	public String getPrepStmtCacheSize() {
		return prepStmtCacheSize;
	}

	public void setPrepStmtCacheSize(String prepStmtCacheSize) {
		this.prepStmtCacheSize = prepStmtCacheSize;
	}

	public String getPrepStmtCacheSqlLimit() {
		return prepStmtCacheSqlLimit;
	}

	public void setPrepStmtCacheSqlLimit(String prepStmtCacheSqlLimit) {
		this.prepStmtCacheSqlLimit = prepStmtCacheSqlLimit;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public Integer getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(Integer maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}
}
