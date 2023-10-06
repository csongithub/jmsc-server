/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.Directory;

/**
 * @author anuhr
 *
 */
public interface DirectoryRepository extends JpaRepository<Directory, Long> {

	public List<Directory> findByClientId(Long clientId);
	
	public Optional<Directory> findById(Long id);
	
	Optional<Directory> findByClientIdAndId(Long clientId, Long id);
	
}
