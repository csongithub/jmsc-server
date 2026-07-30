/**
 * 
 */
package com.jmsc.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
@Entity
@Table(name = "BG_GROUP")
public class BgGroup extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7838539447006194680L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "GROUP_NAME")
	private String groupName;
	
	@NotNull
	@Column(name = "GROUP_INFO")
	private String groupInfo;
	
	@Nullable
	@Column(name = "GROUP_LIMIT")
	private Long groupLimit;
	
	
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
		BgGroup other = (BgGroup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
