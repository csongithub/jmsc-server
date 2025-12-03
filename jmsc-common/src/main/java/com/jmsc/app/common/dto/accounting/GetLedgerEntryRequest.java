/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;

import com.jmsc.app.common.enums.LedgerEntryType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GetLedgerEntryRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6725770777984938066L;

	private long clientId;
	
	private long creditorId;
	
	private long ledgerId;
	
	private LedgerEntryType entryType;
	
	private String from;
	
	private String to;
}
