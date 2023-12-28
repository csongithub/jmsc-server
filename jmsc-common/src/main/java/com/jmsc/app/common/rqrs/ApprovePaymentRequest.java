/**
 * 
 */
package com.jmsc.app.common.rqrs;

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
public class ApprovePaymentRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3465415351761032415L;
	
	@JsonProperty("client_id")
	private Long clientId;
	
	@JsonProperty("payments")
	private List<Long> payments;
}
