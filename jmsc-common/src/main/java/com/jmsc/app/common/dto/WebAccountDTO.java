/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author chandan
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper=false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class WebAccountDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1976931673577685663L;
	
	private Long id;

	private Long clientId;
	
	private String name;
	
	private String url;
	
	private List<AttributeDTO> attributes;
}
