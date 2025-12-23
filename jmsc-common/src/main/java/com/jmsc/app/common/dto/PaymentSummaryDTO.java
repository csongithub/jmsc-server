/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jmsc.app.common.enums.EPaymentStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chandan
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper=false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentSummaryDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7255307312257396306L;
	
	//This is id of parent object PaymentDTO
	private Long paymentId;
	
	private Long partyId;
	
	private String partyName;
	
	private String partyNickName;
	
	/**
	 * Name of to account holder party name
	 */
	private String accountHolder;
	
	private String accountNumber;
	
	/**
	 * This is database id, not the bank account id.
	 * Link bank to bank account id
	 */
	private Long fromAccountId;
	
	/**
	 * This is database id, not the bank account id.
	 * Link bank to bank account id.
	 */
	private Long toAccountId;
	
	private Long amount;
	
	private String amountInWords;
	
	/**
	 * Either machine/site/personal/other
	 */
	private String reason;
	
	/**
	 * Name of site or machine or other
	 */
	private String reasonName;
	
	/**
	 * machine id / site id etc
	 */
	private Long reasonId;
	
	private String mode;
	
	/**
	 * This moght be either cheque no, online transaction no, UTR no etc
	 */
	private String transactionRef;
	
	private String remark;
	
	private Date paymentDate;
	
	private boolean print;
	
	
}
