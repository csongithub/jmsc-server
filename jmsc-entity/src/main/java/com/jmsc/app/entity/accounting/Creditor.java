/**
 * 
 */
package com.jmsc.app.entity.accounting;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Type;


import com.jmsc.app.entity.BaseEntity;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "CREDITOR")
public class Creditor extends BaseEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -6668106638123920237L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	
	@NotNull
	@Column(name = "NAME")
	private String name;
	
	
	@NotNull
	@Column(name = "ADDRESS")
	private String address;
	
	
	@NotNull
	@Column(name = "PARTY_ID")
	private Long partyId;
	
	
	@Type(JsonBinaryType.class)
	@Column(name = "ITEMS")
	private String items;
	
	
	
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
		Creditor other = (Creditor) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
