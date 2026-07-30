/**
 * 
 */
package com.jmsc.app.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2899482330401514356L;
	
	@NotNull
	@Column(name = "CLIENT_ID")
	private Long clientId;

	@Column(name = "CREATED_TS",  insertable = true, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date createdTimestamp;
	
	
	@Column(name = "UPDATED_TS",  insertable = true, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date updatedTimestamp;
}