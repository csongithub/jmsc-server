/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UpdatePasswordResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3586529216810656702L;
	
	private boolean status;
	
	private String message;

	public UpdatePasswordResponse() {
	}
	
	public UpdatePasswordResponse(boolean status, String message) {
		this.status = status;
		this.message = message;
	}

	
}
