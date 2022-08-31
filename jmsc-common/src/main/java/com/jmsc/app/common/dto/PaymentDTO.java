/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jmsc.app.common.enums.EPaymentStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6924985268974618413L;

	private Long id;
	
	private Long clientId;
	
	private String payment;
	
	//This is json string of PaymentSummaryDTO
	private String paymentSummary;
	
	private Date paymentDate;
	
	private EPaymentStatus status;
}
