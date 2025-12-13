/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.RefreshToken;

/**
 * @author anuhr
 *
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
	Optional<RefreshToken> findByClientIdAndUsernameAndToken(Long clientId, String user, String token);
	
	List<RefreshToken> findByClientIdAndUsername(Long clientId, String user);
	
//	public void deleteByClientIdAndUserAndToken(Long clientId, String user, String token);
}