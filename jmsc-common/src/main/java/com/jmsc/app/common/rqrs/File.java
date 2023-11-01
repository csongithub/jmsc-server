/**
 * 
 */
package com.jmsc.app.common.rqrs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author anuhr
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {
	
	private byte[] data;
	
	private String fileName;
	
	private String contentType;
}
