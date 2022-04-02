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
public class OnlineDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4715255836356988772L;
	
	private Long amount;
	
	private String transactionNumber;
	
	/**
	 * {Net Banking, Debit Card, Credit Card, UPI}
	 */
	private String paymentMode;
	
	private String status; 
}