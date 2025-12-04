/**
 * 
 */
package com.jmsc.app.entity.accounting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jmsc.app.entity.BaseEntity;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "CREDITOR_PAYMENT_LINKAGE")
public class CreditorPaymentLinkage extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 5380520316558397723L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "PARTY_ID")
	private Long partyId;
	
	@Column(name = "CREDITOR_ID")
	private Long creditorId;
	
	@Column(name = "PAYMENT_ID")
	private Long paymentId;
	
	@Column(name = "STATUS")
	private String status; //CREATED, ACCEPTED, REJECTED
	
	
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
		CreditorPaymentLinkage other = (CreditorPaymentLinkage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
