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
 *
 */
@Data
@Entity
@Table(name = "STOCK")
public class Stock extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7716853723026716240L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "STOCK_NAME")
	private String stockName;
	
	@NotNull
	@Column(name = "UNIT")
	private String stockUnit;
	
	
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
	
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "CREATION_DATE")
	private Date creationDate;
	
	/**
	 * Last Transaction Date, either debit or credit
	 */
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATED")
	private Date lastUpdated;
	
	
	@NotNull
	@Column(name = "STATUS")
	private String status; //RUNNING, CLOSE
	
	
	
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
		Stock other = (Stock) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
