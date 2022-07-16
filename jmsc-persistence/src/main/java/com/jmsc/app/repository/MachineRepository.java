/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.Machine;

/**
 * @author Chandan
 *
 */
public interface MachineRepository extends JpaRepository<Machine, Long> {

	public List<Machine> findByClientId(Long clientId);
	
	public Optional<Machine> findByName(String name);
}
