/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PermisssionsDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6361262330429157336L;
	
	private boolean canLogin;
	
	private boolean addUsers;
	
	private boolean createPayments;
	
	private boolean approvePayments;
	
	private boolean deletePayments;
}
