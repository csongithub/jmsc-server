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
public class AdminAuthResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8534214261768275865L;
	
	private boolean authorized;
	
	private String message; 

}
