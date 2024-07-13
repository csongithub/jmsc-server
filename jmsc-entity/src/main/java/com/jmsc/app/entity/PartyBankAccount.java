/**
 * 
 */
package com.jmsc.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jmsc.app.common.enums.EBankAccountStatus;
import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
@Entity
@Table(name = "PARTY_BANK_ACCOUNT")
public class PartyBankAccount extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4318031963046510465L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "PARTY_NAME")
	private String partyName;
	
	@NotNull
	@Column(name = "ACCOUNT_HOLDER")
	private String accountHolder;
	
	@Column(name = "BANK_NAME")
	private String bankName;
	
	@Column(name = "BRANCH_NAME")
	private String branchName;
	
	@Column(name = "BRANCH_CODE")
	private String branchCode;
	
	@NotNull
	@Column(name = "ACCOUNT_NO")
	private String accountNumber;
	
	@NotNull
	@Column(name = "IFSC_CODE")
	private String ifscCode;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private EBankAccountStatus status;
	
	
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
		PartyBankAccount other = (PartyBankAccount) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
