/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.BgGroup;

/**
 * @author Chandan
 *
 */
public interface BgGroupRepository extends JpaRepository<BgGroup, Long> {
	
	Optional<BgGroup> findByClientIdAndGroupName(Long clientId, String groupName);
	
	List<BgGroup> findAllByClientId(Long clientId);
	
	Optional<BgGroup> findByClientIdAndId(Long clientId, Long id);

}
