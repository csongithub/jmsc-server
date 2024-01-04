/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jmsc.app.common.rqrs.Range;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper=false)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PaymentFilterCriteria implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3875037623795415469L;
	
	private String fromAccount;
	
	private String toAccount;
	
	private String partyName;
	
	private Range range;
}
