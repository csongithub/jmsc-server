/**
 * 
 */
package com.jmsc.app.entity.accounting;

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

import com.jmsc.app.entity.BaseEntity;
import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author anuhr
 */
@Data
@Entity
@Table(name = "CAPITAL_ACCOUNT")
public class CapitalAccount extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7716853723026716240L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "ACCOUNT_NAME")
	private String accountName;
	
	@NotNull
	@Column(name = "ACCOUNT_TYPE")
	private String accountType; //CASH, BANK_ACCOUNT
	
	/**
	 * During, opening a capital account, this shows the opening balance.
	 * Then after for every transaction either it is debit or credit the balance of 
	 * a capital account will be updated.
	 * 
	 * So Ideally it will be the always latest balance of a capital account.
	 */
	@NotNull
	@Column(name = "BALANCE")
	private Double balance;   
	
	/**
	 * Last Transaction Date, either debit or credit
	 */
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATED")
	private Date lastUpdated;
	
	
	
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
		CapitalAccount other = (CapitalAccount) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
