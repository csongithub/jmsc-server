/**
 * 
 */
package com.jmsc.app.common.wrapper;

import java.io.Serializable;
import java.util.List;

import com.jmsc.app.common.dto.BgGroupDTO;
import com.jmsc.app.common.dto.CreditFacilityDTO;

import lombok.Data;

/**
 * This is a request wrapper for linking Fix Deposit(s) to a Bank Guarantee Group
 * 
 * @author Chandan
 *
 */
@Data
public class LinkFixDepositRequest implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 4832896339606222533L;
	
	private BgGroupDTO bgGroupDTO;
	
	private List<CreditFacilityDTO>  fixDeposits;

}
