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
public class UpdatePasswordRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4940347772285050364L;

	private String logonID;
	
	private String password;
	
	private String newPassword;
	
	private String reNewPassword;
}
