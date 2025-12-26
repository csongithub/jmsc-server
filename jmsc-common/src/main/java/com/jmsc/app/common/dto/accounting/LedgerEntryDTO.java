/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jmsc.app.common.dto.BaseDTO;
import com.jmsc.app.common.enums.EEntryType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LedgerEntryDTO extends BaseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3962138921790234838L;
	
	private Long id;
	
	
	private Long creditorId;
	

	private Long ledgerId;
	
	
	private Long projectId;
	
	
	private String receipt;
	

	private Date date;
	
	
	private String item;
	
	
	private float rate;
	

	private float quantity;
	

	private Double credit;
	
	
	private String unit;
	

	private String vehicle;
	
	
	private String remark;

	
	private EEntryType entryType;
	
	
	private String paymentMode;
	

	/**
	 * This could be either payment id or voucher id just in case if it is paid in cash on site
	 */
	private Long paymentId;
	
	
	private String paymentRefNo;
	

	private Double debit;
	

//	private String narration;
	

	private String user;
	
	private Double total;
	
	private String qtyRate;
	
	/**
	 * This will not be stamp into the database.
	 * This is used when there are payments detected for a creditor.
	 * the value of this could be clientid#partyid#creditorid for example 1#12#10
	 */
	private String tempId;
	
	private String status; //CREATED, ACCEPTED, REJECTED
	
	private boolean validateOnReceipt;
}
