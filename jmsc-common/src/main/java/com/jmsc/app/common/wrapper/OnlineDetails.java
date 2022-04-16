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
	/**
	 * UTR or any transaction reference number
	 */
	private String transactionNumber;
	
	/**
	 * {Net Banking, Debit Card, Credit Card, UPI}
	 */
	private String paymentMode;
	
	/**
	 * detail of account from which payment has been made
	 */
	private String accountDetail;
	/**
	 * Value {SUBMITTED, RETURNED}
	 */
	private String status; 
}