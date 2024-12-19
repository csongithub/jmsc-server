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
	
	private Long id;
	
	private String gstState;
	
	private String fy;
	
	private EFyMonths month;
	
	private Date billDate;
	
	private Date paymentDate;
	
	private Double chequeAmount;
	
	private Double grossAmount;

	private Double taxableAmount;

	private Double gstRate;
	
	private Double cgst;
	
	private Double sgst;
	
	private Double totalGstToPay;
	
	private Double gstDeductedAtSource;
	
	private Double finalGstToPay;
	
	private String sourceDivisionName;
	
	private String projectName;
	
	private String description;
}
