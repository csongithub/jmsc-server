/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PermisssionsDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6361262330429157336L;
	
	private boolean addUser;
	
	private boolean createPayment;
	
	private boolean approvePayments;
	
	private boolean deletePayment;
}
