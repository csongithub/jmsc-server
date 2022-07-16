/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jmsc.app.common.enums.EMachineType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Chandan
 *
 */
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper=false)
public class MachineDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5866758490302680599L;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("client_id")
	private Long clientId;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("owner")
	private String owner;
	
	@JsonProperty("machine_type")
	private EMachineType EMachineType;
	
	@JsonProperty("registration_no")
	private String registrationNo;
	
	@JsonProperty("registration_date")
	private Date registrationDate;
	
	@JsonProperty("chasis_no")
	private String chasisNo;
	
	@JsonProperty("engine_no")
	private String engineNo;
	
	@JsonProperty("insurance_valid_up_to")
	private Date insuranceValitUpTo;
	
	@JsonProperty("permit_valid_up_to")
	private Date permitValidUpTo;
	
	@JsonProperty("tax_valid_up_to")
	private Date taxValidUpTo;
	
	@JsonProperty("pollution_valid_up_to")
	private Date pollutionValidUpTo;
	
	@JsonProperty("fitness_valid_up_to")
	private Date fitnessValidUpTo;
	
	
	public void toUppercase() {
		this.name = this.name.trim().toUpperCase();
		this.owner = this.owner.trim().toUpperCase();
		this.registrationNo = this.registrationNo.trim().toUpperCase();
		this.chasisNo = this.chasisNo.trim().toUpperCase();
		this.engineNo = this.engineNo.trim().toUpperCase();
	}
}
