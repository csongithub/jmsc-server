/**
 * 
 */
package com.jmsc.app.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserDTO {

	@ApiModelProperty(name = "userID", example = "1", notes = "Unique Id (Primary Key) of user", required = false)
	private Long userID;

	@ApiModelProperty(name = "firstName", example = "chandan", notes = "First name of user", required = true)
	private String firstName;
	
	@ApiModelProperty(name = "lastName", example = "singh", notes = "Last name of user", required = true)
	private String lastName;
	
	@ApiModelProperty(name = "mobile", example = "7250605658", notes = "Mobile number  of the user", required = true)
	private String mobile;

	@ApiModelProperty(name = "emailID", example = "cs3088@gmail.com", notes = "Email ID  of the user", required = true)
	private String emailID;
	
	@ApiModelProperty(name = "logonID", example = "chasin3088", notes = "This is JMSC login ID  of the user", required = true)
	private String logonID;
	
	@ApiModelProperty(name = "password", example = "Wel**m*1", notes = "This is JMSC login password  of the user", required = true)
	private String password;
	
	@ApiModelProperty(name = "isFirstLogin", example = "true", notes = "This indicates if the user is login first time", required = true)
	private boolean isFirstLogin;
	
}
