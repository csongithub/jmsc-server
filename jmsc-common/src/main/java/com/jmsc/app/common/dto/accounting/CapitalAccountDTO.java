/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;
import java.util.Date;

import com.jmsc.app.common.dto.BaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CapitalAccountDTO extends BaseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7674956019369642623L;
	
	
	private Long id;
	

	private String accountName;
	

	private String accountType; //CASH, BANK_ACCOUNT

	
	/**
	 * During, opening a capital account, this shows the opening balance.
	 * Then after for every transaction either it is debit or credit the balance of 
	 * a capital account will be updated.
	 * 
	 * So Ideally it will be the always latest balance of a capital account.
	 */

	private Double balance;   
	
	
	/**
	 * Last Transaction Date, either debit or credit
	 */
	private Date lastUpdated;

}
