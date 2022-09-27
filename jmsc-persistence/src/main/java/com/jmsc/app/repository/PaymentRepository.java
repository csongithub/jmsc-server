/**
 * 
 */
package com.jmsc.app.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.common.enums.EPaymentStatus;
import com.jmsc.app.entity.Payment;

/**
 * @author Chandan	
 *
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	List<Payment> findAllByClientId(Long clientId);
	
	List<Payment> findAllByClientIdAndStatusAndPaymentDateBetween(Long clientId, EPaymentStatus status, Date paymentDateStart, Date paymentDateEnd);
	
	List<Payment> findAllByClientIdAndStatus(Long clientId, EPaymentStatus status);
	
	Optional<Payment> findAllByClientIdAndId(Long clientId, Long id);
}
