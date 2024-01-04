/**
 * 
 */
package com.jmsc.app.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jmsc.app.common.enums.EPaymentStatus;
import com.jmsc.app.entity.Payment;

/**
 * @author Chandan	
 *
 */
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	public static final String FILTER_CLIENT = "client_id=:clientId";
	public static final String FILTER_STATUS = "status=:status";
	public static final String FILTER_FROM_ACCOUNT = "UPPER(payment->'from_account'->>'accountNumber') =UPPER(:fromAccountNumber)";
	public static final String FILTER_TO_ACCOUNT = "UPPER(payment->'to_account'->>'accountNumber') =UPPER(:toAccountNumber)";
	public static final String FILTER_PARTY = "(UPPER(payment->'party'->>'name') like upper(concat('%', :name, '%')) OR UPPER(payment->'party'->>'nick_name') like upper(concat('%', :name, '%')))";
	public static final String AND = " AND ";
	public static final String SELECT_PAYMENT_QUERY_PREFIX = "SELECT  * FROM jmsc.payment WHERE ";
	
	List<Payment> findAllByClientId(Long clientId);
	
	List<Payment> findAllByClientIdAndStatusAndPaymentDateBetween(Long clientId, EPaymentStatus status, Date paymentDateStart, Date paymentDateEnd);
	
	List<Payment> findAllByClientIdAndStatus(Long clientId, EPaymentStatus status);
	
	Optional<Payment> findAllByClientIdAndId(Long AND, Long id);
	
	@Query(value = SELECT_PAYMENT_QUERY_PREFIX + FILTER_CLIENT + AND + FILTER_FROM_ACCOUNT + AND + FILTER_STATUS, nativeQuery = true)
	List<Payment> findByFromAccountNumber(@Param("clientId")Long clientId, @Param("fromAccountNumber") String accountNumber, @Param("status")String status);
	
	@Query(value = SELECT_PAYMENT_QUERY_PREFIX +FILTER_CLIENT + AND + FILTER_TO_ACCOUNT + AND + FILTER_STATUS, nativeQuery = true)
	List<Payment> findByToAccountNumber(@Param("clientId")Long clientId, @Param("toAccountNumber") String accountNumber, @Param("status")String status);
	
	@Query(value = SELECT_PAYMENT_QUERY_PREFIX + FILTER_CLIENT + AND + FILTER_FROM_ACCOUNT + AND + FILTER_TO_ACCOUNT +  AND  + FILTER_STATUS, nativeQuery = true)
	List<Payment> findByFromAndToAccount(@Param("clientId")Long clientId, 
										 @Param("fromAccountNumber")String fromAccountNumber, 
										 @Param("toAccountNumber") String toAccountNumber,
										 @Param("status")String status);
	
	@Query(value = SELECT_PAYMENT_QUERY_PREFIX + FILTER_CLIENT + AND + FILTER_PARTY + AND + FILTER_STATUS, nativeQuery = true)
//	@Query(value = "SELECT  * FROM jmsc.payment WHERE client_id=1 AND UPPER(((payment->'party'->>'name'))) like UPPER('%IMPRESSION%') OR UPPER(((payment->'party'->>'nick_name'))) like UPPER('%IMPRESSION%') AND status='APPROVED'",nativeQuery = true )
	List<Payment> findByParty(@Param("clientId")Long clientId, @Param("name") String partyName, @Param("status")String status);
	
	@Query(value = SELECT_PAYMENT_QUERY_PREFIX + FILTER_CLIENT + AND + FILTER_PARTY + AND + FILTER_FROM_ACCOUNT + AND + FILTER_TO_ACCOUNT+ AND + FILTER_STATUS, nativeQuery = true)
	List<Payment> findByPartyAndFromAccountAndToAccount(@Param("clientId")Long clientId,
														@Param("name") String partyName,
														@Param("fromAccountNumber")String fromAccountNumber,
														@Param("toAccountNumber") String toAccountNumber,
														@Param("status")String status);
	
	@Query(value = SELECT_PAYMENT_QUERY_PREFIX + FILTER_CLIENT + AND + FILTER_FROM_ACCOUNT + AND + FILTER_PARTY + AND + FILTER_STATUS, nativeQuery = true)
	List<Payment> findByFromAccountAndPartyName(@Param("clientId")Long clientId,
												@Param("fromAccountNumber")String fromAccountNumber,
												@Param("name") String partyName,
												@Param("status")String status);
	
	
	@Query(value = SELECT_PAYMENT_QUERY_PREFIX + FILTER_CLIENT + AND + FILTER_TO_ACCOUNT + AND + FILTER_PARTY + AND + FILTER_STATUS, nativeQuery = true)
	List<Payment> findByToAccountAndPartyName(@Param("clientId")Long clientId,
												@Param("toAccountNumber")String toAccountNumber,
												@Param("name") String partyName,
												@Param("status")String status);
}
