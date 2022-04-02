/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jmsc.app.common.wrapper.EMDDetails;
import com.jmsc.app.common.wrapper.FeeDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BidCostDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5700528163650172772L;
	
	private Long id;
	
	private Long clientId;
	
	private Long bidId;
	
	private FeeDetails feeDetails;
	
	private EMDDetails emdDetails;
	
	/**
	 * Json only
	 */
	private String otherCost;
}
