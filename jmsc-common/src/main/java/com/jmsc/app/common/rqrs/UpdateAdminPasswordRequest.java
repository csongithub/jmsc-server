/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateAdminPasswordRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4742536148436561310L;
	
	private Long clientId;
	
//	private String logonId;
//	
//	private String password;
	
	private String currentAdminPassword;
	
	private String newAdminPassword;

}
