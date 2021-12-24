/**
 * 
 */
package com.jmsc.app.config.datasource;

/**
 * @author chandan singh
 *
 */
public interface IDataSourceConstants {
	static final String DATA_SOURCE_BEAN_NAME = "primaryDataSource";
	static final String PERSISTENCE_UNIT_NAME = "jmsc";
	static final String DB_MODEL_PACKAGE_SCAN = "com.jmsc.app.entity";
	static final String ENTITY_MANAGER_NAME   = "jmscEntityManager";
	static final String TRANSACTION_MANAGER_NAME = "jmscTransactionManager";
	static final String REPOSITORY_BASE_PACKAGES = "com.jmsc.app.repository";
}
