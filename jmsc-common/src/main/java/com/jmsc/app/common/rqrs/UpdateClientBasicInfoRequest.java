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
public class UpdateClientBasicInfoRequest implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 6136038101245161600L;
	
	private String logonId;
	
	private String name;
	
	private String displayName;

}
