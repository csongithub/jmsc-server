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

import com.jmsc.app.common.enums.ELoanStatus;
import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author Chandan
 *
 */

@Data
@Entity
@Table(name = "LOAN")
public class Loan extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6963022596776398914L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "ACCOUNT_NO")
	private String accountNo;
	
	@NotNull
	@Column(name = "DISPLAY_NAME")
	private String displayName;
	
	@NotNull
	@Column(name = "SANC_AMOUNT")
	private Long sanctionedAmount;
	
	@NotNull
	@Column(name = "DISB_AMOUNT")
	private Long disbursedAmount;
	
	@NotNull
	@Column(name = "EMI_AMOUNT")
	private Long emiAmount;
	
	@NotNull
	@Column(name = "I_RATE")
	private float interestRate;
	
	@NotNull
	@Column(name = "OPENING_DATE")
	private Date openingDate;
	
	@NotNull
	@Column(name = "BANK")
	private String bankName;
	
	@NotNull
	@Column(name = "BRANCH")
	private String branch;
	
	@NotNull
	@Column(name = "BORROWER")
	private String borrower;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private ELoanStatus status;
	
	
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
		Loan other = (Loan) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
