/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
public class AdminAuthRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1165581489605319925L;

	private Long clientId;
	
	private String adminPassword;

}
