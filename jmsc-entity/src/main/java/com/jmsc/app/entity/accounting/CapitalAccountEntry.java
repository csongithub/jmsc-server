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

import com.jmsc.app.common.enums.EEntryType;
import com.jmsc.app.entity.BaseEntity;
import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "CAPITAL_ACCOUNT_ENTRY")
public class CapitalAccountEntry extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7349658742759305361L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	
	@NotNull
	@Column(name = "ACCOUNT_ID")
	private Long accountId;
	
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE")
	private Date date;
	
	
	@NotNull
	@Column(name = "NOTE")
	private String note;
	
	
	@NotNull
	@Column(name = "DEBIT")
	private Double debit;
	
	
	@NotNull
	@Column(name = "CREDIT")
	private Double credit;
	
	
	@NotNull
	@Column(name = "BALANCE")
	private Double balance;
	
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "ENTRY_TYPE")
	private EEntryType entryType;
	
	
	/**
	 * This could be either voucher no (in case of debit type), 
	 * or the payment id (in case of credit type).
	 */
	@Column(name = "TRANS_REF_NO")
	private Long transactionRefNo;
	
	
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
		CapitalAccountEntry other = (CapitalAccountEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
