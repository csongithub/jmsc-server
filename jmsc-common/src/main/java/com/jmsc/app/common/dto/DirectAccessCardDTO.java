/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chandan
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectAccessCardDTO  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1629424123163737059L;
	
	private String title;
	
	private String description;
	
	private String url;
}
