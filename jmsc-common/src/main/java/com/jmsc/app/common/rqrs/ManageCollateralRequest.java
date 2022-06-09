/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;
import java.util.List;

import com.jmsc.app.common.dto.CreditFacilityDTO;
import com.jmsc.app.common.dto.LoanDTO;

import lombok.Data;

/**
 * @author Chandan
 *
 */
@Data
public class ManageCollateralRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9159064729830725941L;
	
	
	private LoanDTO loan;
	
	private List<CreditFacilityDTO>  collateral;
	
	private Boolean link;

}
