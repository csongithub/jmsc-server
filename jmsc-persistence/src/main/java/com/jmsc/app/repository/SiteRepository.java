/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.Site;

/**
 * @author Chandan
 *
 */
public interface SiteRepository extends JpaRepository<Site, Long> {

	public Optional<Site> findBySiteNameOrDisplayName(String siteName, String displayName);
	
	public List<Site> findByClientId(Long clientId);
}
