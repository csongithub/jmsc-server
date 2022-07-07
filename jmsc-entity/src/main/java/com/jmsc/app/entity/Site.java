/**
 * 
 */
package com.jmsc.app.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
@Entity
public class Site extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -381745943053272394L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "SITE_NAME")
	private String siteName;
	
	@NotNull
	@Column(name = "DISPLAY_NAME")
	private String displayName;
	
	@Nullable
	@Column(name = "BID_LINKAGE_ID")
	private Long bidLinkageId;
	
	@Nullable
	@Column(name = "AGREEMENT_NO")
	private String agreementNo;
	
	@Nullable
	@Temporal(TemporalType.DATE)
	@Column(name = "AGREEMENT_DATE")
	private Date agreementDate;
	
	@Nullable
	@Column(name = "AGREEMENT_VALUE")
	private Long agreementValue;
	
	
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
		Site other = (Site) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
