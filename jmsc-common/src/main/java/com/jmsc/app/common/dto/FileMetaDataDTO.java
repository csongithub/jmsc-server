/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jmsc.app.common.enums.EFileStatus;
import com.jmsc.app.common.enums.EFileType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class FileMetaDataDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5515476750266783010L;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("directory_id")
	private Long directoryId;
	
	@JsonProperty("system_path")
	private String systemPath;
	
	@JsonProperty("file_name")
	private String fileName;
	
	@JsonProperty("file_size")
	private String fileSize;
	
	@JsonProperty("file_type")
	private EFileType fileType;
	
	@JsonProperty("file_path")
	private String filePath;
	
	@JsonProperty("created_by")
	private String createdBy;
	
	@JsonProperty("updated_by")
	private String updatedBy;
	
	@JsonProperty("status")
	private EFileStatus status;
	
	@JsonProperty("description")
	private String description;
}
