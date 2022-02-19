/**
 * 
 */
package com.jmsc.app.entity.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
@Entity
@Table(name = "PAYMENT_DRAFT")
public class PaymentDraft extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6703228999839709912L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "CLIENT_ID")
	private Long clientId;
	
	@NotNull
	@Column(name = "STATUS")
	private String status;
	
	@NotNull
	@Column(name = "DRAFT")
	private byte[] draft;
	
	
	
	
	
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
		PaymentDraft other = (PaymentDraft) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
