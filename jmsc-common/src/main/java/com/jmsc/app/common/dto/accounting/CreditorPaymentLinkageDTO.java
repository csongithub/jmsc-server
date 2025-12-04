/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;

import com.jmsc.app.common.dto.BaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CreditorPaymentLinkageDTO extends BaseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9037652791977791118L;
	
	
	private Long id;
	
	private Long partyId;
	
	private Long creditorId;
	
	private Long paymentId;
	
	private String status; //CREATED, ACCEPTED, REJECTED
}
