/**
 * 
 */
package com.jmsc.app.common.wrapper;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
public class FeeDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5167259457061864440L;

	private String feeMode;
	
	private List<OfflineFeeDetails> offlineFeeDetails;
	
	private OnlineDetails onlineFeeDetails;
	
	private String comments;
}