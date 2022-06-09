/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jmsc.app.common.enums.ELoanStatus;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
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
