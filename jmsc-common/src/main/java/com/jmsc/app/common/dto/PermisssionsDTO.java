/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermisssionsDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6361262330429157336L;
	
	private boolean canLogin;
	
	private boolean addUsers;
	
	/************** Payment Permissions ***************/
	private boolean createPayments;
	
	private boolean approvePayments;
	
	private boolean deletePayments;
	
	/************** Party & Party Account Permissions ***************/
	private boolean addParty;
	
	private boolean deleteParty;
	
	private boolean updatePartyBankAccountStatus;
	
	private boolean updatePartyBankAccount;
	
	/************** Machine Permissions ***************/
	
	private boolean addMachine;
	
	private boolean updateMachine;
	
	private boolean deleteMachine;
	
	
}
