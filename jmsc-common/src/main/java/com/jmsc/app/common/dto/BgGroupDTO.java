/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class BgGroupDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8131869298815907776L;
	
	
	private Long id;
	
	
	private Long clientId;
	
	
	private String groupName;
	

	private String groupInfo;
	
	private Long groupLimit;
	
	private List<CreditFacilityDTO> linkedBgList;
	
	
	private List<CreditFacilityDTO> linkedFdList;
	
	
}
