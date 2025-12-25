/**
 * 
 */
package com.jmsc.app.service;

import java.util.Date;

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
	public boolean isNotNull(Long val) {
		return !isNull(val);
	}
	
	
	public boolean isNull(String val) {
		return val == null;
	}
	public boolean isNotNull(String val) {
		return !isNull(val);
	}
	
	
	public boolean isNull(Object obj) {
		return obj == null ? true : false;
	}
	public boolean isnotNull(Object obj) {
		return !isNull(obj);
	}
	
	
	
	public boolean isNull(Date val) {
		return val == null ? true : false;
	}
	public boolean isNotNull(Date val) {
		return !isNull(val);
	}
}
