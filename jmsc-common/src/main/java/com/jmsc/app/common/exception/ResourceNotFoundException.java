/**
 * 
 */
package com.jmsc.app.common.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception is thrown when a Resource is not found 
 * 
 * @author chandan singh
 *
 */
@Slf4j
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
		log.error(message);
	}

}