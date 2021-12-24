/**
 * 
 */
package com.jmsc.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.users.PaymentDraft;

/**
 * @author Chandan
 *
 */
public interface PaymentDraftRepository extends JpaRepository<PaymentDraft, Long>{

}
