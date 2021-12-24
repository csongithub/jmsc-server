/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;

import com.jmsc.app.common.dto.UserDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class LoginResponse implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 5371443882125747783L;
	
	private boolean loginSuccess;
	
	private UserDTO userDTO;
}
