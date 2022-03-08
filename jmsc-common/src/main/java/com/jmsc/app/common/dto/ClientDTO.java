package com.jmsc.app.common.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ClientDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3135693249345118801L;
	
	@ApiModelProperty(name = "id", example = "1", notes = "Unique Id (Primary Key) of the client", required = false)
	private Long id;

	@ApiModelProperty(name = "name", example = "JMSC", notes = "Name of the client", required = true)
	private String name;
	
	@ApiModelProperty(name = "displayName", example = "JMSC", notes = "Display name of the client", required = true)
	private String displayName;
	
	@ApiModelProperty(name = "status", example = "ACTIVE", notes = "Status of the client", required = true)
	private String status;
	
	@ApiModelProperty(name = "logonId", example = "jmsc001", notes = "Login id for the client", required = true)
	private String logonId;
	
	@ApiModelProperty(name = "password", example = "1", notes = "Password of the client", required = true)
	private String password;
	
	@ApiModelProperty(name = "recipients", example = "jaimaa072@gmail.com", notes = "Email recipients of the client", required = false)
	private String recipients;
	
	
	public void removePassword() {
		this.password = "";
	}

}
