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
public class GetCapAccountEntryRequest implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1255271464218609159L;

	private long clientId;
	
	private long accountId;
	
	private EEntryType entryType;
	
	private String from;
	
	private String to;
}
