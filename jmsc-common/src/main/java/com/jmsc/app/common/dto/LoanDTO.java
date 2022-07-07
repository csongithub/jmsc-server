/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jmsc.app.common.enums.ELoanStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Chandan
 *
 */
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper=false)
public class LoanDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4389289449307177580L;
	
	private Long id;
	
	private Long clientId;
	
	private String accountNo;
	
	private String displayName;
	
	private Long sanctionedAmount;
	
	private Long disbursedAmount;

	private Long emiAmount;
	
	private float interestRate;

	private Date openingDate;
	
	private String bankName;
	
	private String branch;

	private String borrower;
	
	private ELoanStatus status;

}
