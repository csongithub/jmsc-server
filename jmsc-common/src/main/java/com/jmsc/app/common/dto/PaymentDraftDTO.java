/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentDraftDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4267062542257008430L;

	@ApiModelProperty(name = "id", example = "1", notes = "Unique Id (Primary Key) for draft", required = false)
	private Long id;
	
	@ApiModelProperty(name = "id", example = "1", notes = "Client ID", required = false)
	private Long clientId;
	
	@ApiModelProperty(name = "status", example = "Printed", notes = "status of draft", required = true)
	private String status;
	
	@ApiModelProperty(name = "draftJson", example = "", notes = "draft json data", required = true)
	private String draftJson;
}
