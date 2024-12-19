/**
 * 
 */
package com.jmsc.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jmsc.app.common.enums.EFyMonths;
import com.sun.istack.NotNull;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "E_INVOICE")
public class EInvoice extends BaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1929530240487948898L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "GST_STATE")
	private String gstState;
	
	@NotNull
	@Column(name = "FY")
	private String fy;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "MONTH")
	private EFyMonths month;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "BILL_DATE")
	private Date billDate;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "PAYMENT_DATE")
	private Date paymentDate;
	
	@NotNull
	@Column(name = "CHEQUE_AMOUNT")
	private Double chequeAmount;
	
	@NotNull
	@Column(name = "GROSS_AMOUNT")
	private Double grossAmount;
	
	@NotNull
	@Column(name = "TAXABLE_AMOUNT")
	private Double taxableAmount;
	
	@NotNull
	@Column(name = "GST_RATE")
	private Double gstRate;
	
	@NotNull
	@Column(name = "CGST")
	private Double cgst;
	
	@NotNull
	@Column(name = "SGST")
	private Double sgst;
	
	@NotNull
	@Column(name = "TOTAL_GST_TO_PAY")
	private Double totalGstToPay;
	
	@NotNull
	@Column(name = "GST_DEDUCTED_AT_SOURCE")
	private Double gstDeductedAtSource;
	
	@NotNull
	@Column(name = "FINAL_GST_TO_PAY")
	private Double finalGstToPay;
	
	@NotNull
	@Column(name = "SOURCE_DIVISION_NAME")
	private String sourceDivisionName;
	
	@NotNull
	@Column(name = "PROJECT_NAME")
	private String projectName;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	
	
	
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
		EInvoice other = (EInvoice) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
