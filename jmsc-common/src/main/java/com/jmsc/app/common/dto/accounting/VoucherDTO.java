/**
 * 
 */
package com.jmsc.app.common.dto.accounting;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.jmsc.app.common.dto.BaseDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class VoucherDTO extends BaseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2160496314029319158L;
	
	
	private Long id;
	
	private String voucherNo;
	
	private Date date;
	
	/**
	 * json string of list of {@link VoucherItem}
	 */
	private String items;
	
	@Transient
	private List<VoucherItem> list;
	
	private Double amount;
	
	private Long capitalAccountId;
	
	private Long projectId;

}
