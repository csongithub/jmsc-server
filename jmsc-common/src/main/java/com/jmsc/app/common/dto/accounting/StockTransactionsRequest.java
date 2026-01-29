/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;

import com.jmsc.app.common.enums.EEntryType;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
public class StockTransactionsRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2483783605689379909L;
	
	private long clientId;
	
	private long stockId;
	
	private EEntryType entryType;
	
	private String from;
	
	private String to;
}
