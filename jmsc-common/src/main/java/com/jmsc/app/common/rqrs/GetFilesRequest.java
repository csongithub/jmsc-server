/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class GetFilesRequest implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -3561534930353885701L;
	
	@JsonProperty("client_id")
	private Long clientId;
	
	@JsonProperty("directory_id")
	private Long directoryId;
	
	@JsonProperty("system_path")
	private String systemPath;
}
