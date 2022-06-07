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
 * This is a request wrapper for linking Bank Guarantee(s) to a Bank Guarantee Group
 * 
 * @author Chandan
 *
 */
@Data
public class ManageBankGuaranteeRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7255683581560055420L;

	private BgGroupDTO bgGroupDTO;
	
	private List<CreditFacilityDTO>  bankGuarantees;
	
	private Boolean link;
}
