/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.Project;

/**
 * @author anuhr
 *
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	
	public List<Project> findAllByClientId(Long clientId);
	
	
	
}
