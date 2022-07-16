/**
 * 
 */
package com.jmsc.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jmsc.app.common.enums.EMachineType;
import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
@Entity
@Table(name = "MACHINE")
public class Machine extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2707214100009704840L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "NAME")
	private String name;
	
	@NotNull
	@Column(name = "OWNER")
	private String owner;
	
	@Column(name = "MACHINE_TYPE")
	private EMachineType EMachineType;
	
	@Column(name = "REGI_NO")
	private String registrationNo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "REGI_DATE")
	private Date registrationDate;
	
	@Column(name = "CHASIS_NO")
	private String chasisNo;
	
	@Column(name = "ENGINE_NO")
	private String engineNo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "INSURANCE_EXPIRY")
	private Date insuranceValitUpTo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "PERMIT_EXPIRY")
	private Date permitValidUpTo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "TAX_EXPIRY")
	private Date taxValidUpTo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "POLLUTION_EXPIRY")
	private Date pollutionValidUpTo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FITNESS_EXPIRY")
	private Date fitnessValidUpTo;
	
	
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
		Machine other = (Machine) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
