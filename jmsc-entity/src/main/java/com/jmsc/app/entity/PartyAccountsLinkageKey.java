/**
 * 
 */
package com.jmsc.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Embeddable
public class PartyAccountsLinkageKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2055347197750036139L;

	public PartyAccountsLinkageKey() {
		super();
		
	}
	
	

	public PartyAccountsLinkageKey(Long clientId, Long partyId, Long accountId) {
		super();
		this.clientId = clientId;
		this.partyId = partyId;
		this.accountId = accountId;
	}



	@Column(name = "CLIENT_ID")
	private Long clientId;
	
	@Column(name = "PARTY_ID")
	private Long partyId;
	
	@Column(name = "ACCOUNT_ID")
	private Long accountId;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((partyId == null) ? 0 : partyId.hashCode());
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
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
		PartyAccountsLinkageKey other = (PartyAccountsLinkageKey) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		
		if (partyId == null) {
			if (other.partyId != null)
				return false;
		} else if (!partyId.equals(other.partyId))
			return false;
		
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		
		return true;
	}

}
