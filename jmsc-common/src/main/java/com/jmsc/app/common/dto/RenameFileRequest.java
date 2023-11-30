/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RenameFileRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7972870350841758488L;
	@JsonProperty("client_id")
	Long clientId;
	
	@JsonProperty("directory_id")
	Long directoryId;
	
	@JsonProperty("file_id")
	Long fileId;
	
	@JsonProperty("name")
	String newName;
	
	@JsonProperty("description")
	String newDescription;

}
