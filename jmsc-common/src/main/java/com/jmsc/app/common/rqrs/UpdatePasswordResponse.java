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
public class UpdatePasswordResponse implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -4832307813057169600L;

	private boolean updateSuccess;
	
	private String message;
}
