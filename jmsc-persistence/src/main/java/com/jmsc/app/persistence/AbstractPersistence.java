/**
 * 
 */
package com.jmsc.app.persistence;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jmsc.app.config.datasource.IDataSourceConstants;

/**
 * @author chandan
 *
 */
public abstract class AbstractPersistence {
	
	
	@Autowired
	@Qualifier(IDataSourceConstants.ENTITY_MANAGER_NAME)
	protected EntityManager em;
	

}
