/**
 * 
 */
package com.jmsc.app.common.wrapper;

import java.io.Serializable;

import com.jmsc.app.common.dto.BgGroupDTO;

import lombok.Data;

/**
 * This wrappers is the complete wrapper for a BG Group
 * This contains the bg group details along with the facilities that are 
 * linked to a group
 * 
 * @author Chandan
 *
 */
@Data
public class BgGroupWrapper implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6290221531223131616L;
	
	private BgGroupDTO bgGroup;
	
	private CreditFacilityWrapper facilityWrapper;

}
