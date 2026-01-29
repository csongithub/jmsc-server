/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import com.jmsc.app.common.dto.BaseDTO;
import com.jmsc.app.common.enums.EEntryType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 */
@Data
@EqualsAndHashCode(callSuper=false)
//@JsonIgnoreProperties(ignoreUnknown = true)
public class StockTransactionDTO extends BaseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3577349604855552224L;
	
	private Long id;
	
	
	private Long stockId;
	
	//This will be set when a creditor provide the supply in or also when supply from this stock goes to a creditor
	private Long creditorId;
		
	//This will be set when a creditor provide the supply in or also when supply from this stock goes to a creditor
	private Long ledgerId;
		
	//This will be set when supply from this stock goes to a project
	private Long projectId;
	
	
	private Date date;
	
	
	private String note;
	
	
	private Double debit;
	
	
	private Double credit;
	
	/**
	 * This will be set in case of supply only, the value of total supply
	 */
	private Double amount;
	
	
	private EEntryType entryType;
	
	
	private String transactionRefNo;
	
	/**
	 * will be set only during statement fetching for a period
	 */
	@Transient
	private Double balance;
}
