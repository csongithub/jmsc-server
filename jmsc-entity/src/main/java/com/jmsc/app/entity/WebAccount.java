/**
 * 
 */
package com.jmsc.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author chandan
 *
 */
@Data
@Entity
@Table(name = "WEB_ACCOUNT")
public class WebAccount extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2536504145659865163L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "ACCOUNT_NAME")
	private String name;
	
	@NotNull
	@Column(name = "URL")
	private String url;
	
	@NotNull
	@Column(name = "ATTRIBUTES")
	private byte[] attributesByte;
	
	
	
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
		WebAccount other = (WebAccount) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
