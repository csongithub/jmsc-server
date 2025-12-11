package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;
import java.util.Date;

import com.jmsc.app.common.dto.BaseDTO;
import com.jmsc.app.common.enums.EEntryType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CapitalAccountEntryDTO extends BaseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8029820532497167570L;
	
	
	private Long id;
	
	
	private Long accountId;
	
	
	private Date date;
	
	

	private String note;
	
	

	private Double debit;
	
	

	private Double credit;
	
	

	private Double balance;
	
	

	private EEntryType entryType;
	/**
	 * This could be either voucher no (in case of debit type), 
	 * or the payment id (in case of credit type).
	 */
	private Long transactionRefNo;
}
