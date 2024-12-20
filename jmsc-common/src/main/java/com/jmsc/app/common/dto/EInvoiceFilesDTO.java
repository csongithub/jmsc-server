/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class EInvoiceFilesDTO extends BaseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4046012944051432445L;
	
	private Long id;
	
	private Long invoiceId;
	
	private boolean memoAttached;
	
	private byte[] memo;
	
	private String memoFileName;

	private String memoContentType;
	
	private boolean invoiceAttached;
	
	private byte[] invoice;
	
	private String invoiceFileName;
	
	private String invoiceContentType;
	
	private String invoiceNumber;

}
