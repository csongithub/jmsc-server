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

import org.hibernate.annotations.Type;

import com.jmsc.app.entity.BaseEntity;
import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "LEDGER")
public class Ledger extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3242997689022929028L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "CREDITOR_ID")
	private Long creditorId;
	
	
	@NotNull
	@Column(name = "CODE")
	private String code;
	
	
	@NotNull
	@Column(name = "NAME")
	private String name;
	
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "START_DATE")
	private Date startDate;
	
	
	@NotNull
	@Column(name = "OPENING_BALALCE")
	private Double openingBalance;
	
	
	@NotNull
	@Column(name = "REMARK")
	private String remark;
	
	
	@NotNull
	@Type(type="jsonb")
	@Column(name = "COLUMNS")
	private String columns;
	
	
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
		Ledger other = (Ledger) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
