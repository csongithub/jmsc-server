/**
 * 
 */
package com.jmsc.app.entity;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "REFRESH_TOKEN")
public class RefreshToken extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9010978522154800161L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@NotNull
	@Column(name = "TOKEN")
    private String token;

	@NotNull
	@Column(name = "USERNAME")
    private String username;

	@NotNull
	@Column(name = "EXPIRY")
    private Instant expiryDate;
	
	
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
		RefreshToken other = (RefreshToken) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
