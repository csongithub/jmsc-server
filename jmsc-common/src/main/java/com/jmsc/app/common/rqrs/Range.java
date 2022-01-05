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
public class Range implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -17236729257950617L;
	
	private String from;
	
	private String to;
}
