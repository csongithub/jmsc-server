/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
public class TurnOverDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7001216731771597598L;

	private String year;
	
	private String turnover;
	
	private String label;
	
	private String value;
	
}
