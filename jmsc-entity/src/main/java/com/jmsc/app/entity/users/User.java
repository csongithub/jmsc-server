/**
 * 
 */
package com.jmsc.app.entity.users;

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
@Table(name = "USERS")
public class User extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2754930586724427936L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long userID;
	
	@NotNull
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@NotNull
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@NotNull
	@Column(name = "MOBILE")
	private String mobile;
	
	@NotNull
	@Column(name = "EMAIL_ID")
	private String emailID;
	
	@NotNull
	@Column(name = "LOGON_ID")
	private String logonID;
	
	@NotNull
	@Column(name = "PASSWORD")
	private String password;
	
	@NotNull
	@Column(name = "FIRST_LOGIN")
	private boolean isFirstLogin;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
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
		User other = (User) obj;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}
}
