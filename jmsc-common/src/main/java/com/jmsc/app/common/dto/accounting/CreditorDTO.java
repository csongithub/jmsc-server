/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;

import com.jmsc.app.common.dto.BaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CreditorDTO extends BaseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5324289140788293728L;
	
	
	private Long id;
	
	
	private String name;
	
	
	private String address;
	
	
	private Long partyId;
	
	
	private String items;
	
	public void toUpperCase() {
		this.name = name.toUpperCase();
		this.address = address.toUpperCase();
	}
}
