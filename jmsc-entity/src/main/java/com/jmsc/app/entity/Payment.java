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

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.jmsc.app.common.enums.EPaymentStatus;
import com.sun.istack.NotNull;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
@Entity
@Table(name = "PAYMENT")
@TypeDefs({@TypeDef(name = "jsonb", typeClass  = JsonBinaryType.class)})
public class Payment extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3222449467227110064L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	
	@NotNull
	@Type(type="jsonb")
	@Column(name = "PAYMENT")
	private String payment;
	
	@NotNull
	@Type(type="jsonb")
	@Column(name = "PAYMENT_SUMMARY")
	private String paymentSummary;
	
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "PAYMENT_DATE")
	private Date paymentDate;
	
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private EPaymentStatus status;
	
	
	
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
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
