/**
 * 
 */
package com.jmsc.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jmsc.app.common.enums.EBidStatus;
import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
@Entity
@Table(name = "BID")
public class Bid extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5541109320710575410L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "CLIENT_ID")
	private Long clientId;
	
	@NotNull
	@Column(name = "AUTHORITY")
	private String bidAuthority;
	
	@NotNull
	@Column(name = "NIT")
	private String 	nit;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "NIT_DATE")
	private Date 	nitDate;
	
	@NotNull
	@Column(name = "TENDER_REF_NUMBER")
	private String 	tenderRefNumber;
	
	@NotNull
	@Column(name = "TENDER_ID")
	private String 	tenderId;
	
	@NotNull
	@Column(name = "TITLE")
	private String 	title;
	
	@NotNull
	@Column(name = "WORK_VALUE")
	private Long 	workValue;
	
	@NotNull
	@Column(name = "BIDDING_COST")
	private Long 	biddingCost;
	
	@NotNull
	@Column(name = "BC_IN_FAVOUR")
	private String 	bcInFavourOf;
	
	@NotNull
	@Column(name = "BC_PAYBLE_AT")
	private String bcPaybleAt;
	
	@NotNull
	@Column(name = "EMD_AMOUNT")
	private Long 	emdAmount;
	
	@NotNull
	@Column(name = "EMD_IN_FAVOUR")
	private String 	emdInFavourOf;
	
	@NotNull
	@Column(name = "EMD_PAYBLE_AT")
	private String emdPaybleAt;
	
	@Column(name = "BANK_CERTIFICATE")
	private Long 	bankCertificate;
	
	@NotNull
	@Column(name = "WORK_PERIOD")
	private Long	periodOfWork;
	
	@NotNull
	@Column(name = "BID_VALIDITY")
	private Long	bidValidity;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "BID_START_DATE")
	private Date	bidStartDate;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "BID_END_DATE")
	private Date	bidEndDate;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "BID_OPENING_DATE")
	private Date	bidOpeningDate;
	
	@NotNull
	@Column(name = "BIDDING_RATE")
	private Double	biddingRate;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private EBidStatus status;
	
	@Column(name = "REASON")
	private String 	reason;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "BID_SUBMITTED_DATE")
	private Date bidSubmittedDate;
	/**
	 * This is after bid is submitted
	 */
	@Column(name = "BID_ID")
	private Long bidId;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bid other = (Bid) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
