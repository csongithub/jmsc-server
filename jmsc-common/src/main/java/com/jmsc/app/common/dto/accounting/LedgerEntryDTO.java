/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;
import java.util.Date;

import com.jmsc.app.common.dto.BaseDTO;
import com.jmsc.app.common.enums.LedgerEntryType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
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

	
	private LedgerEntryType entryType;
	
	
	private String paymentMode;
	

	private Long paymentId;
	
	
	private String paymentRefNo;
	

	private Double debit;
	

	private String narration;
	

	private String user;
	
	private Double total;
	
	private String qtyRate;
}
