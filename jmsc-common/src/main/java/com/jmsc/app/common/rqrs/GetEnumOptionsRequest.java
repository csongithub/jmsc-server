/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
public class GetEnumOptionsRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1847368151167342017L;

	private String enumName;
	
	private List<String> skipList = new ArrayList<String>();
}
