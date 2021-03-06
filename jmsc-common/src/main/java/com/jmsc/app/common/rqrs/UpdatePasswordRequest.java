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
public class UpdatePasswordRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 122839155873733705L;
	
	private String logonId;
	
	private String currentPassword;
	
	private String newPassword;
}
