/**
 * 
 */
package com.jmsc.app.service;

/**
 * @author anuhr
 *
 */
public abstract class AbstractService {
	
	
	public boolean isCreate(Long id) {
		if(id == null)
			return true;
		else
			return false;
	}
	
	
	public boolean isUpdate(Long id) {
		return !isCreate(id);
	}
	
	
	public boolean isNull(Long val) {
		return val == null ? true : false;
	}
	
	
	public boolean isNull(String val) {
		return val == null ? true : false;
	}
	
	
	public boolean isNull(Object obj) {
		return obj == null ? true : false;
	}
}
