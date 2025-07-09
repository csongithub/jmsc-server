/**
 * 
 */
package com.jmsc.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "PURCHAGE_LEDGER")
public class PurchageLedger extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2766078192471518102L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	
	@NotNull
	@Column(name = "SUPPLIER_ID")
	private Long supplierId;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE")
	private Date date;
	
	
	
	@Column(name = "ITEM")
	private String item;
	
	
	
	@Column(name = "QTY")
	private Double quantity;
	
	

	@Column(name = "RATE")
	private Double rate;
	
	
	@Column(name = "VEHICLE")
	private String vehicle;
	
	
	
	@Column(name = "CREDIT_AMOUNT")
	private Double creditAmount;
	
	

	@Column(name = "DEBIT_AMOUNT")
	private Double debitAmount;
	
	
	@Column(name = "REMARK")
	private String remark;
	
	
	
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
		PurchageLedger other = (PurchageLedger) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
