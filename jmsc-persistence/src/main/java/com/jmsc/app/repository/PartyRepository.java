/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.common.enums.EPartyStatus;
import com.jmsc.app.entity.Party;

/**
 * @author Chandan
 *
 */
public interface PartyRepository extends JpaRepository<Party, Long> {
	
	public List<Party> findByClientId(Long clientId);
	
	public List<Party> findByClientIdAndStatus(Long clientId, EPartyStatus status);
	
	public Optional<Party> findByNameOrNickName(String name, String nickName);
	
	public Optional<Party> findByClientIdAndId(Long clientId, Long id);
	
}
