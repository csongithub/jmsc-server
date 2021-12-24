package com.jmsc.app.common.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Credentials implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8745296638234711728L;
	
	private String logonID;
	
	private String password;
}
