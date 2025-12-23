/**
 * 
 */
package com.jmsc.app.repository.accounting;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.accounting.Voucher;

/**
 * @author anuhr
 *
 */
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
	
	Optional<Voucher> findByClientIdAndIdAndCapitalAccountId(Long clientId, Long Id, Long capitalAccountId);

}
