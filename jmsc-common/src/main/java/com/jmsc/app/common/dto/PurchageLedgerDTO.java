/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PurchageLedgerDTO extends BaseDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5155698551094953809L;
	
	
	private Long id;
	
	
	private Long supplierId;
	
	
	private Date date;
	
	
	private String item;
	
	
	private Double quantity;
	
	
	private Double rate;
	

	private String vehicle;
	
	
	private Double creditAmount;
	
	
	private Double debitAmount;
	

	private String remark;
}
