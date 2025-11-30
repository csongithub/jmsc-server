/**
 * 
 */
package com.jmsc.app.entity.accounting;

import java.io.Serializable;
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

import com.jmsc.app.common.enums.LedgerEntryType;
import com.jmsc.app.entity.BaseEntity;
import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "LEDGER_ENTRY")
public class LedgerEntry extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5991743985282940438L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "CREDITOR_ID")
	private Long creditorId;
	
	@NotNull
	@Column(name = "LEDGER_ID")
	private Long ledgerId;
	
	@NotNull
	@Column(name = "PROJECT_ID")
	private Long projectId;
	
	@NotNull
	@Column(name = "RECEIPT_NO")
	private String receipt;
	
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE")
	private Date date;
	
	

	@Column(name = "ITEM")
	private String item;
	
	

	@Column(name = "RATE")
	private float rate;
	

	@Column(name = "QTY")
	private float quantity;
	
	

	@Column(name = "CREDIT")
	private Double credit;
	
	

	@Column(name = "UNIT")
	private String unit;
	
	
	@Column(name = "VEHICLE")
	private String vehicle;
	
	
	@Column(name = "REMARK")
	private String remark;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "ENTRY_TYPE")
	private LedgerEntryType entryType;
	
	@Column(name = "PAYMENT_MODE")
	private String paymentMode; //Cheque, Online, Cash, UPI
	
//	link to @{PaymentDTO}
	@Column(name = "PAYMENT_ID") 
	private Long paymentId;
	
	@Column(name = "PAYMENT_REF_NO") 
	private String paymentRefNo;
	
	@Column(name = "DEBIT")
	private Double debit;
	
	@Column(name = "NARRATION")
	private String narration;
	
	@Column(name = "USER_NAME")
	private String user; //who made this entry into system
	
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
		LedgerEntry other = (LedgerEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
