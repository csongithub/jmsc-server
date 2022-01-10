package com.jmsc.app.common.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class PartyBankAccountDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4274623343549838175L;

	@ApiModelProperty(name = "id", example = "1", notes = "Unique Id (Primary Key) of bank account", required = false)
	private Long id;
	
	@ApiModelProperty(name = "partyName", example = "Siddhivinayak Fuel Center", notes = "Name of party", required = true)
	private String partyName;

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
	
	public void toUppercase() {
		if(partyName != null)
			partyName = partyName.toUpperCase();
		
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
	}
}
