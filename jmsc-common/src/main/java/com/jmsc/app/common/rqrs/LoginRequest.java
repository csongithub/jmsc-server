/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;

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
public class LoginRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9159086766840594818L;
	
	private String logonId;
	
	private String password;

}
