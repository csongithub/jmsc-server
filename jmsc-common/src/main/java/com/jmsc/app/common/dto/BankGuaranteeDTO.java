/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.jmsc.app.common.enums.EBankGuaranteeCreationType;
import com.jmsc.app.common.enums.EBankGuaranteeSecurityType;
import com.jmsc.app.common.enums.EBankGuaranteeStatus;
import com.jmsc.app.common.enums.EBankGuaranteeType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BankGuaranteeDTO extends BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2491550007725686701L;
	



	private Long id;
	
	private EBankGuaranteeCreationType creationType;
	
	private EBankGuaranteeType type;
	
	private String bgNumber;
	
	private Long bgAmount;
	
	private Date validFrom;
	
	private Date validTo;
	
	private String inFavourOf;
	
	private String workName;
	
	private String bank;
	
	private EBankGuaranteeSecurityType security;
	
	private EBankGuaranteeStatus status;
	
	private Long charge;
	
	private Date chargedOn;
	
	private String chargedFromAccount;
	
	private boolean fileAttached;
}
