/**
 * 
 */
package com.jmsc.app.config.datasource;

import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chandan
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = IDataSourceConstants.ENTITY_MANAGER_NAME, transactionManagerRef = IDataSourceConstants.TRANSACTION_MANAGER_NAME, basePackages = IDataSourceConstants.REPOSITORY_BASE_PACKAGES)
@Slf4j
public class PrimaryPersistanceConfig {

	@Autowired
	PrimaryDataSourceConfig primaryDataSourceConfig;

	@Bean(name = IDataSourceConstants.DATA_SOURCE_BEAN_NAME)
	@Primary
	public HikariDataSource primaryDataSource() {

		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(primaryDataSourceConfig.getJdbcUrl());
		config.setUsername(primaryDataSourceConfig.getUsername());
		config.setPassword(primaryDataSourceConfig.getPassword());

		if (primaryDataSourceConfig.getPoolName() != null)
			config.setPoolName(primaryDataSourceConfig.getPoolName());

		if (primaryDataSourceConfig.getMaximumPoolSize() != null)
			config.setMaximumPoolSize(primaryDataSourceConfig.getMaximumPoolSize().intValue());

		if (primaryDataSourceConfig.getMaximumPoolSize() != null)
			config.setConnectionTestQuery(primaryDataSourceConfig.getConnectionTestQuery());

		if (primaryDataSourceConfig.getConnectionInitSql() != null)
			config.setConnectionInitSql(primaryDataSourceConfig.getConnectionInitSql());

		config.addDataSourceProperty("cachePrepStmts", primaryDataSourceConfig.getCachePrepStmts());
		config.addDataSourceProperty("prepStmtCacheSize", primaryDataSourceConfig.getPrepStmtCacheSize());
		config.addDataSourceProperty("prepStmtCacheSqlLimit", primaryDataSourceConfig.getPrepStmtCacheSqlLimit());
		HikariDataSource hikariDataSource = new HikariDataSource(config);

		Set<Entry<Object, Object>> entrySet = config.getDataSourceProperties().entrySet();
		for (Entry<Object, Object> property : entrySet)
			log.debug("{} - {} ", property.getKey(), property.getValue());
		log.debug("{}", primaryDataSourceConfig);
		return hikariDataSource;
	}

	@Bean(name = IDataSourceConstants.ENTITY_MANAGER_NAME)
	@Primary
	public LocalContainerEntityManagerFactoryBean worklyEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(primaryDataSource()).packages(IDataSourceConstants.DB_MODEL_PACKAGE_SCAN)
				.persistenceUnit(IDataSourceConstants.PERSISTENCE_UNIT_NAME).build();
	}

	@Bean(name = IDataSourceConstants.TRANSACTION_MANAGER_NAME)
	@Primary
	public PlatformTransactionManager worklyTransactionManager(
			@Qualifier(IDataSourceConstants.ENTITY_MANAGER_NAME) EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}
