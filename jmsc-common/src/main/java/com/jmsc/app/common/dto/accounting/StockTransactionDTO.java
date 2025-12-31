/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;

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
	
	
	private String note;
	
	
	private Double debit;
	
	
	private Double credit;
	
	
	private EEntryType entryType;
	
	
	private String transactionRefNo;
}
