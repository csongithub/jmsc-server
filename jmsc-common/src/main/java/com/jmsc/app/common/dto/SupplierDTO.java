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
public class SupplierDTO extends BaseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8675265275763351516L;
	
	private Long id;
	
	private Long partyId;
	
	private String supplyConfig;
}
