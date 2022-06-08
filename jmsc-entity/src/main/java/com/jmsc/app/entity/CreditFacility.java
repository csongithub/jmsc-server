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

import com.jmsc.app.common.enums.EFacilityIssuerType;
import com.jmsc.app.common.enums.EFacility;
import com.jmsc.app.common.enums.EPledgedType;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
@Entity
@Table(name = "CREDIT_FACILITY")
public class CreditFacility extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5485160457225939525L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "CLIENT_ID")
	private Long clientId;
	
	@NotNull
	@Column(name = "ACCOUNT_NO")
	private String accountNumber;
	
	@NotNull
	@Column(name = "AMOUNT")
	private Long amount;
	
	@NotNull
	@Column(name = "OPEN_DATE",  insertable = true, updatable = true)
	@Temporal(TemporalType.DATE)
	private Date openDate;
	
	@NotNull
	@Column(name = "MATURITY_DATE",  insertable = true, updatable = true)
	@Temporal(TemporalType.DATE)
	private Date maturityDate;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "ISSUER_TYPE")
	private EFacilityIssuerType issuerType;
	
	@NotNull
	@Column(name = "ISSUER_NAME")
	private String issuerName;
	
	@NotNull
	@Column(name = "ISSUER_BRANCH")
	private String issuerBranch;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	@Column(name = "FACILITY_TYPE")
	private EFacility facilityType;
	
	/**
	 * This is 
	 */
	@Nullable
	@Column(name = "IS_PLEDGED")
	private Boolean isPledged;
	
	/**
	 * Linked BID ID
	 */
	@Nullable
	@Column(name = "PLEDGED_ID")
	private Long pledgedId;
	
	@Nullable
	@Enumerated(EnumType.STRING)
	@Column(name = "PLEDGED_TYPE")
	private EPledgedType pledgedType;
	
	/**
	 * For a Fix Deposit, this is the BG Group Id, in which the FixDeposit is linked/hold
	 * For a BankGuarantee this is the BG Group in which a bank guarantee belongs to
	 */
	@Nullable
	@Column(name = "BG_GROUP_ID")
	private Long bgGroupId;
	
	
	/**
	 * For a Fix Deposit, this is the Loan Id, in which the FixDeposit is linked/hold
	 * 
	 */
	@Nullable
	@Column(name = "LOAN_ID")
	private Long loanId;
	
	
	
	
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
		CreditFacility other = (CreditFacility) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
