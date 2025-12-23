package com.jmsc.app.entity.accounting;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.jmsc.app.entity.BaseEntity;
import com.sun.istack.NotNull;

import lombok.Data;


@Data
@Entity
@Table(name = "VOUCHER")
public class Voucher extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1619221966339686536L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@NotNull
	@Column(name = "VOUCHER_NO")
	private String voucherNo;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE")
	private Date date;
	
	@NotNull
	@Type(type="jsonb")
	@Column(name = "ITEMS")
	private String items;
	
	@NotNull
	@Column(name = "AMOUNT")
	private Double amount;
	
	@NotNull
	@Column(name = "CAP_ACC_ID")
	private Long capitalAccountId;
	
	@NotNull
	@Column(name = "PROJECT_ID")
	private Long projectId;
	
	@NotNull
	@Column(name = "CREATOR")
	private String creator; // Name of the creator
	
	@NotNull
	@Column(name = "APPROVER")
	private String approver; //Name of the user who approves it
	
	
	@NotNull
	@Column(name = "STATUS")
	private String status; //CREATED, APPROVED
	
	
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
		Voucher other = (Voucher) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
