/**
 * 
 */
package com.jmsc.app.common.wrapper;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
public class OfflineFeeDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8100569218264379178L;
	
	/**
	 * DD,FD,NSC
	 */
	private String mode;
	
	private String issuer;
	
	private String instrumentNo;
	
	private Long amount;

	private Date issueDate;
	
	private Date expiryDate;
}
