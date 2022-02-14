/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;

import com.jmsc.app.common.dto.ClientDTO;

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
public class LoginResponse implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 5371443882125747783L;
	
	private boolean loginSuccess;
	
	private ClientDTO clientDTO;
	
	private String message;
}
