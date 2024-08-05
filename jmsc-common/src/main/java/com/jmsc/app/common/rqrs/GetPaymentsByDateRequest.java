/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GetPaymentsByDateRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1016093855371597232L;
	
	private Range range;
}
