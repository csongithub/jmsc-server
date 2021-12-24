/**
 * 
 */
package com.jmsc.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.users.PartyBankAccount;

/**
 * @author Chandan
 *
 */
public interface PartyBankAccountRepository extends JpaRepository<PartyBankAccount, Long> {

}
