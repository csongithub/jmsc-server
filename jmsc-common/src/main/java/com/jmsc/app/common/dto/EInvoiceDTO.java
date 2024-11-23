/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

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
	
	private String gstState;
	
	private String fy;
	
	private EFyMonths month;
	
	private Date billDate;
	
	private Date paymentDate;
	
	private Long chequeAmount;
	
	private Long grossAmount;

	private Long taxableAmount;

	private Long gstRate;
	
	private Long cgst;
	
	private Long sgst;
	
	private Long totalGstToPay;
	
	private Long gstDeductedAtSource;
	
	private Long finalGstToPay;
	
	private String sourceDivisionName;
	
	private String projectName;
	
	private String description;
}
