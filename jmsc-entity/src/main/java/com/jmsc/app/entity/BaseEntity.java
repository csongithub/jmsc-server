/**
 * 
 */
package com.jmsc.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2899482330401514356L;


	@Column(name = "CREATED_TS",  insertable = true, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date createdTimestamp;
	
	
	@Column(name = "UPDATED_TS",  insertable = true, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	private Date updatedTimestamp;
}