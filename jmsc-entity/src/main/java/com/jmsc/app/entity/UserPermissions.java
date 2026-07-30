/**
 * 
 */
package com.jmsc.app.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Type;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "USER_PERMISSIONS")
public class UserPermissions extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5473695203370178583L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@NotNull
	@Type(JsonBinaryType.class)
	@Column(name = "PERMISSIONS")
	private String permissionsJson;
	
	
	
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
		UserPermissions other = (UserPermissions) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
