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
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
	private String message;
	private String detail;
	private Date timestamp;
}
