/**
 * 
 */
package com.jmsc.app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.PaymentDraft;

/**
 * @author Chandan
 *
 */
public interface PaymentDraftRepository extends JpaRepository<PaymentDraft, Long>{
	
	List<PaymentDraft> findAllByClientId(Long clientId);
	
	List<PaymentDraft> findAllByClientIdAndUpdatedTimestampBetween(Long clientId, Date updatedTimestampStart, Date updatedTimestampEnd);
}
