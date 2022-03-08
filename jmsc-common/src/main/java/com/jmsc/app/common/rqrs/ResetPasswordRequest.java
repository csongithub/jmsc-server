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
public class ResetPasswordRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3307862804108380603L;
	
	private String logonId;
	
	private String newPassword;
}
