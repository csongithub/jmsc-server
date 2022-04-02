/**
 * 
 */
package com.jmsc.app.common.wrapper;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
public class FeeDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5167259457061864440L;

	private String feeInstrumentNo;
	
	private Long feeAmount;

	private Date issueDate;
	
	private Date expiryDate;
	
	private String issuer;
	
	private boolean isOnline;
	
	private OnlineDetails onlineDetails;
	
	private String comments;
}