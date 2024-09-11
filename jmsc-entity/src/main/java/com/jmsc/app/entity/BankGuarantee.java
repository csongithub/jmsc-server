/**
 * 
 */
package com.jmsc.app.entity;

import java.io.Serializable;
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

import org.springframework.lang.Nullable;

import com.jmsc.app.common.enums.EBankGuaranteeCreationType;
import com.jmsc.app.common.enums.EBankGuaranteeSecurityType;
import com.jmsc.app.common.enums.EBankGuaranteeStatus;
import com.jmsc.app.common.enums.EBankGuaranteeType;
import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "BANK_GUARANTEE")
public class BankGuarantee extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1629842294116940766L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "CREATION_TYPE")
	private EBankGuaranteeCreationType creationType;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "BG_TYPE")
	private EBankGuaranteeType type;
	
	@NotNull
	@Column(name = "BG_NUMBER")
	private String bgNumber;
	
	@NotNull
	@Column(name = "BG_AMOUNT")
	private Long bgAmount;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "VALID_FROM_DATE")
	private Date validFrom;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "VALID_TO_DATE")
	private Date validTo;
	
	/**
	 * Name of Division, this guarantee is issues in favour of
	 * Example: EE, RWD, Work DIVISION, SIMDEGA
	 */
	@NotNull
	@Column(name = "IN_FAVOUR_OF")
	private String inFavourOf;
	
	/**
	 * Title/Package/Name/Description of the the Work (Along with Agreement No if any)
	 */
	@NotNull
	@Column(name = "WORK_NAME")
	private String workName;
	
	@NotNull
	@Column(name = "BANK")
	private String bank;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "SECURITY_TYPE")
	private EBankGuaranteeSecurityType security;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private EBankGuaranteeStatus status;
	
	@Nullable
	@Column(name = "BG_CHARGE")
	private Long charge;
	
	@Nullable
	@Temporal(TemporalType.DATE)
	@Column(name = "CHARGE_DATE")
	private Date chargedOn;
	
	@Nullable
	@Column(name = "CHARGE_FROM_ACCOUNT")
	private String chargedFromAccount;
	
	@NotNull
	@Column(name = "FILE_ATTACHED")
	private boolean fileAttached;
	
	@Column(name = "FILE")
	private byte[] file;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "CONTENT_TYPE")
	private String contentType;
	
	
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
		BankGuarantee other = (BankGuarantee) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
