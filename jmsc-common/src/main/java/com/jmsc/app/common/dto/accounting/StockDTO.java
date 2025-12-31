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
public class StockDTO extends BaseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1790959980900518265L;
	
	
	private Long id;
	
	private String stockName;
	
	private String stockUnit;
	
	private Double balance;
	
	private Date creationDate;
	
	private Date lastUpdated;
	
	private String status;
}
