/**
 * 
 */
package com.jmsc.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "PROJECT")
public class Project extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4800286616281216549L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "CLIENT_NAME")
	private String clientName;
	
	@NotNull
	@Column(name = "NICK_NAME")
	private String nickName;
	
	@NotNull
	@Column(name = "FULL_NAME")
	private String fullName;
	
	@Column(name = "PACKAGE_NO")
	private String packageNo;
	
	@NotNull
	@Column(name = "AGREEMENT_NO")
	private String agreementNo;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "AGREEMENT_DATE")
	private Date agreementDate;
	
	@NotNull
	@Column(name = "AGREEMENT_AMOUNT")
	private Double agreementAmount;
	
	@NotNull
	@Column(name = "CONST_AMOUNT")
	private Double constructionAmount;
	
	@Column(name = "MAINT_AMOUNT")
	private Double maintenanceAmount;
	
	@Column(name = "OTHER_AMOUNT")
	private Double otherAmount;
	
	@Column(name = "SECURITY_AMOUNT")
	private Double securityAmount;
	
	
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
		Project other = (Project) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
