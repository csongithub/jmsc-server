/**
 * 
 */
package com.jmsc.app.common.wrapper;

import java.io.Serializable;
import java.util.List;

import com.jmsc.app.common.dto.CreditFacilityDTO;

import lombok.Data;

/**
 * 
 * This class is a wrapper for holding list of facility category wise.
 * For Example: For a BG group it can have all fixed deposits that are linked in it.
 * Also it can have all bank guarantees which are issue in this group
 * 
 * @author Chandan
 *
 */
@Data
public class CreditFacilityWrapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2989436599109579598L;
	
	
	List<CreditFacilityDTO> guaranteeList;
	
	List<CreditFacilityDTO> depositList;
}
