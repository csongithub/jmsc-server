/**
 * 
 */
package com.jmsc.app.common.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chandan
 *
 */
@Data
public class ExceptionResponse {
	private String message;
	private String detail;
	private Date timestamp;
	
	
	public ExceptionResponse() {}


	public ExceptionResponse(String message, String detail, Date timestamp) {
		super();
		this.message = message;
		this.detail = detail;
		this.timestamp = timestamp;
	}
}
