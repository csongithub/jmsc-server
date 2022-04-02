/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.jmsc.app.common.enums.EBidStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class BidDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6889714986957643067L;

	private Long id;
	
	private Long clientId;
	
	private String bidAuthority;
	
	private String 	nit;

	private Date 	nitDate;

	private String 	tenderRefNumber;
	
	private String 	tenderId;
	

	private String 	title;
	

	private Long 	workValue;
	
	private Long 	biddingCost;
	
	private String 	bcInFavourOf;
	
	private String bcPaybleAt;
	
	private Long 	emdAmount;
	
	private String 	emdInFavourOf;
	
	private String 	emdPaybleAt;
	
	private Long 	bankCertificate;
	

	private Long	periodOfWork;
	
	
	private Long	bidValidity;
	

	private Date	bidStartDate;
	

	private Date	bidEndDate;
	

	private Date	bidOpeningDate;
	

	private Double	biddingRate;
	

	private EBidStatus status;
	
	
	private String 	reason;
	
	
	private Date bidSubmittedDate;
	
	
	private Long bidId;
}
