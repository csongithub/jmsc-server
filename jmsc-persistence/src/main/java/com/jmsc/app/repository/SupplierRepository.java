/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.Supplier;

/**
 * @author anuhr
 *
 */
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	
	public List<Supplier> findAllByClientId(Long clientId);
	
	public Optional<Supplier> findByClientIdAndId(Long clientId, Long id);

}
