/**
 * 
 */
package com.jmsc.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.BidCost;

/**
 * @author Chandan
 *
 */
public interface BidCostRepository extends JpaRepository<BidCost, Long> {

	Optional<BidCost> findByBidId(Long bidId);
}
