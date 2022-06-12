/**
 * 
 */
package com.jmsc.app.common.rqrs;

import java.io.Serializable;

import com.jmsc.app.common.dto.BgGroupDTO;
import com.jmsc.app.common.dto.BidDTO;
import com.jmsc.app.common.dto.LoanDTO;
import com.jmsc.app.common.enums.EFacility;
import com.jmsc.app.common.enums.EFacilityIssuerType;
import com.jmsc.app.common.enums.EFacilityLinkageType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class FacilityLinkageDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3016800621824620560L;
	
	private LoanDTO loan;
	
	private BidDTO bid;
	
	private BgGroupDTO bgGroup;
	
	private EFacilityLinkageType linkageType;
	
	private EFacility facilityType;
	
	private String facilityAccountNo;
	
	private Long facilityId;
	
	private String issuer;
	
	private Long facilityAmount;

}
