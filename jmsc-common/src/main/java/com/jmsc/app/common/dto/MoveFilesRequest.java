/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MoveFilesRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6444630195291245549L;
	
	@JsonProperty("client_id")
	Long clientId;
	
	@JsonProperty("directory_id")
	Long directoryId;
	
	@JsonProperty("files")
	List<Long> files;
	
	@JsonProperty("new_path")
	String newPath;
}
