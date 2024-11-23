/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
public class BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7587038121930456347L;
	
	private Long clientId;
	
	protected Long id;

	private Date createdTimestamp;
	
	private Date updatedTimestamp;
	
	
	public boolean isCreate() {
		return this.id == null ? true : false;
	}
	
	public boolean isUpdate() {
		return !this.isCreate();
	}
}
