/**
 * 
 */
package com.jmsc.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "E_INVOICE_FILE")
public class EInvoiceFile implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2120833206878498744L;

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "MEMO")
	private byte[] memo;
	
	@Column(name = "MEMO_NAME")
	private String memoFileName;

	@Column(name = "MEMO_TYPE")
	private String memoContentType;
	
	@Column(name = "INVOICE")
	private byte[] invoice;
	
	@Column(name = "INVOICE_NAME")
	private String invoiceFileName;
	
	@Column(name = "INVOICE_TYPE")
	private String invoiceContentType;
	
	@Column(name = "INVOICE_NUMBER")
	private String invoiceNumber;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EInvoiceFile other = (EInvoiceFile) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
