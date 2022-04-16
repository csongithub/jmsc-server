/**
 * 
 */
package com.jmsc.app.common.wrapper;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
public class OtherBidCost implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6118057989523400903L;

	private Long clientId;
	
	private Long bidId;
	
	private String otherCostJson;
}
