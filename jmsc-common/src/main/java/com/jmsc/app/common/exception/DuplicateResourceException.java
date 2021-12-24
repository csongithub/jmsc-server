package com.jmsc.app.common.exception;


/**
 * Exception is thrown when trying to persist a duplicate resaource
 * 
 * @author chandan singh
 *
 */
public class DuplicateResourceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateResourceException(String message) {
		super(message);
	}

}