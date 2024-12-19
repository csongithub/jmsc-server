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
	
	
	public boolean isupdate(Long id) {
		return !isCreate(id);
	}

}
