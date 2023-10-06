/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class DirectoryDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7107065372073415478L;
	
	@ApiModelProperty(name = "id", example = "1", notes = "Unique Id (Primary Key) of the client", required = false)
	private Long id;
	
	private String name;
	
	private String description;
	
	private String category;

}
