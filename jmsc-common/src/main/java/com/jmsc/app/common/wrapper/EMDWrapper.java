/**
 * 
 */
package com.jmsc.app.common.wrapper;

import java.io.Serializable;

import com.jmsc.app.common.dto.CreditFacilityDTO;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
public class EMDWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6547949226894341237L;
	
	private CreditFacilityDTO emd;
	
	/**
	 * Value {SUBMITTED, RETURNED}
	 */
	private String status;
}