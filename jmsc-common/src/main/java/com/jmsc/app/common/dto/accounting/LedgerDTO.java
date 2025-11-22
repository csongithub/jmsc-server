/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;
import java.util.Date;

import com.jmsc.app.common.dto.BaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class LedgerDTO extends BaseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7563011828264309123L;
	
	
	private Long id;
	
	
	private Long creditorId;
	
	
	private String code;
	
	
	private String name;
	
	
	private Date startDate;
	
	
	private Double openingBalance;
	
	
	private String remark;
}
