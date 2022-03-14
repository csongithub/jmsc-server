/**
 * 
 */
package com.jmsc.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.common.enums.EBidStatus;
import com.jmsc.app.entity.Bid;

/**
 * @author Chandan
 *
 */
public interface BidRepository extends JpaRepository<Bid, Long> {

	List<Bid> findAllByClientId(Long clientId);
	
	List<Bid> findAllByClientIdAndStatus(Long clientId, EBidStatus status);
}
