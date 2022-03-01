/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.jmsc.app.common.enums.EFacilityIssuerType;
import com.jmsc.app.common.enums.EPledgedType;
import com.jmsc.app.common.enums.EFacilityType;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CreditFacilityDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2060533414478139805L;
	

	private Long id;
	
	private Long clientId;
	
	private String accountNumber;
	
	private Long amount;
	
	private Date openDate;
	
	private Date maturityDate;
	
	private EFacilityIssuerType issuerType;

	private String issuerName;
	
	private String issuerBranch;

	private EFacilityType facilityType;

	private Boolean isPledged;

	private Long pledgedId;
	
	private EPledgedType pledgedType;

}
