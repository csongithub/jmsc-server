/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jmsc.app.common.enums.EPartyStatus;
import com.jmsc.app.common.enums.EPartyType;

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
public class PartyDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6671078809927127416L;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("client_id")
	private Long clientId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("nick_name")
	private String nickName;
	
	@JsonProperty("party_type")
	private EPartyType partyType;
	
	@JsonProperty("mobile")
	private String mobile;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("status")
	private EPartyStatus status;
	
	public void toUppercase() {
		this.name = this.name.trim().toUpperCase();
		this.nickName = this.nickName.trim().toUpperCase();
	}
}
