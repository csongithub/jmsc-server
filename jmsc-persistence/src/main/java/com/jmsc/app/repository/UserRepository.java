/**
 * 
 */
package com.jmsc.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jmsc.app.entity.users.User;

/**
 * @author chandan
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.logonID = ?1")
	public Optional<User> findByLogonID(String logonID);
	
}
