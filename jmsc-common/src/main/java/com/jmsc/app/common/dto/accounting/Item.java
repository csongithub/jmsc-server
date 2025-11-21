/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4963198208707063135L;
	
	
	private String label;
	
	private Long value;
}
