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
	
	
	@Column(name = "RECEIPT_NO")
	private String receipt;
	
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE")
	private Date date;
	
	
	@NotNull
	@Column(name = "ITEM")
	private String item;
	
	
	@NotNull
	@Column(name = "RATE")
	private float rate;
	
	@NotNull
	@Column(name = "QTY")
	private float quantity;
	
	
	@NotNull
	@Column(name = "CREDIT")
	private Double credit;
	
	
	@NotNull
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
	
	
	@NotNull
	@Column(name = "DEBIT")
	private Double debit;
	
	
	@Column(name = "NARRATION")
	private String narration;
	
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
