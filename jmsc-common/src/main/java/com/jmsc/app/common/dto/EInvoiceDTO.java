/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import com.jmsc.app.common.enums.EFyMonths;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class EInvoiceDTO  extends BaseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8565279951515913512L;
	
	private Long id;
	
	private String gstState;
	
	private String fy;
	
	private EFyMonths month;
	
	private Long grossAmount;
	
	private Long chequeAmount;
	
	private Long gstRate;

	private Long taxableAmount;

	private Long totalGsttoPay;

	private Long gstDeductedAtSource;

	private Long finalGstLiability;

	private String source;
	
	private String work;
	
	private String description;
	

}
