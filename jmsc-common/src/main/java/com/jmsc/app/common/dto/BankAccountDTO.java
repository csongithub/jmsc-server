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
public class BankAccountDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4587765474863345206L;

	@ApiModelProperty(name = "id", example = "1", notes = "Unique Id (Primary Key) of bank account", required = false)
	private Long id;

	@ApiModelProperty(name = "accountHolder", example = "Siddhivinayak Fuel Center", notes = "Name of bank account holder", required = true)
	private String accountHolder;
	
	@ApiModelProperty(name = "bankName", example = "State Bank Of India", notes = "Name of bank", required = false)
	private String bankName;
	
	@ApiModelProperty(name = "branchName", example = "Aurangabad", notes = "Branch of the bank", required = false)
	private String branchName;
	
	@ApiModelProperty(name = "branchCode", example = "034567", notes = "Branch code of the bank", required = false)
	private String branchCode;
	
	@ApiModelProperty(name = "accountNumber", example = "20012693476", notes = "Account number", required = true)
	private String accountNumber;
	
	@ApiModelProperty(name = "ifscCode", example = "SBIN12576", notes = "IFSC code of the bank", required = true)
	private String ifscCode;
	
	@ApiModelProperty(name = "address", example = "Aurngabad, Bazar, Bihar-824101", notes = "Address of the bank", required = false)
	private String address;
	
	@ApiModelProperty(name = "mobileNo", example = "7250605658", notes = "Registred Mobile Number", required = true)
	private String mobileNo;
	
	@ApiModelProperty(name = "acccountType", example = "Current", notes = "Bank account type", required = true)
	private String acccountType;
	
	@ApiModelProperty(name = "status", example = "Active", notes = "Bank account status", required = true)
	private String status;
	
	
	private String displayName; 
	
	
	public BankAccountDTO toUppercase() {
		if(accountHolder != null)
			accountHolder = accountHolder.toUpperCase();
		
		if(bankName != null)
			bankName = bankName.toUpperCase();
		
		if(branchName != null)
			branchName = branchName.toUpperCase();
		
		if(branchCode != null)
			branchCode = branchCode.toUpperCase();
		
		if(accountNumber != null)
			accountNumber = accountNumber.toUpperCase();
		
		if(ifscCode != null)
			ifscCode = ifscCode.toUpperCase();
		
		if(address != null)
			address = address.toUpperCase();
		
		if(mobileNo != null)
			mobileNo = mobileNo.toUpperCase();
		
		if(acccountType != null)
			acccountType = acccountType.toUpperCase();
		
		if(status != null)
			status = status.toUpperCase();
		
		return this;
	}
}
