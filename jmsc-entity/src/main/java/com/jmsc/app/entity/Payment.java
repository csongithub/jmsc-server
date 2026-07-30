package com.jmsc.app.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.jmsc.app.common.enums.EPaymentStatus;
import jakarta.validation.constraints.NotNull;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.Data;

@Data
@Entity
@Table(name = "PAYMENT")
public class Payment extends BaseEntity {

	private static final long serialVersionUID = -3222449467227110064L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@NotNull
	@Type(JsonBinaryType.class)
	@Column(name = "PAYMENT", columnDefinition = "jsonb")
	private String payment;

	@NotNull
	@Type(JsonBinaryType.class)
	@Column(name = "PAYMENT_SUMMARY", columnDefinition = "jsonb")
	private String paymentSummary;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "PAYMENT_DATE")
	private Date paymentDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private EPaymentStatus status;

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
		Payment other = (Payment) obj;
		if (id == null) {
			return other.id == null;
		} else return id.equals(other.id);
	}
}