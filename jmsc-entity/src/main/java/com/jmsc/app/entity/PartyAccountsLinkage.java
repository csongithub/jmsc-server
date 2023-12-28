/**
 * 
 */
package com.jmsc.app.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
@Entity
@Table(name = "PARTY_ACCOUNT_LINKAGE")
public class PartyAccountsLinkage{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7914249348388501960L;
	
	@EmbeddedId
	private PartyAccountsLinkageKey id;
	
	
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
		PartyAccountsLinkage other = (PartyAccountsLinkage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
