/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class PartyAcountsLinkageDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6834062028543046893L;

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("client_id")
	private Long clientId;
	
	@JsonProperty("party_id")
	private Long partyId;
	
	@JsonProperty("account_id")
	private Long accuontId;
}
