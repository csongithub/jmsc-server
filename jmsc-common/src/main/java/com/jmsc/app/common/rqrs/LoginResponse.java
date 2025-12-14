/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;

import com.jmsc.app.common.dto.ClientDTO;
import com.jmsc.app.common.dto.PermisssionsDTO;
import com.jmsc.app.common.dto.UserDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class LoginResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5371443882125747783L;
	
	private boolean loginSuccess;
	
	private ClientDTO clientDTO;
	
	private UserDTO userDTO;
	
	private PermisssionsDTO permissions;
	
	private String message;
	
	private String token;
	
	private String refreshToken;
	
	private boolean isAdmin;
}
