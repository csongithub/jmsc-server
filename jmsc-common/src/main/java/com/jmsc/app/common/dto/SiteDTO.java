/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Chandan
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SiteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6209858608601142433L;
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("client_id")
	private Long clientId;

	@JsonProperty("site_name")
	private String siteName;

	@JsonProperty("display_name")
	private String displayName;

	@JsonProperty("bid_linkage_id")
	private Long bidLinkageId;

	@JsonProperty("agreement_no")
	private String agreementNo;

	@JsonProperty("agreement_date")
	private Date agreementDate;

	@JsonProperty("agreement_value")
	private Long agreementValue;
	
	
	
	public void toUppercase() {
		this.siteName = this.siteName.trim().toUpperCase();
		this.displayName = this.displayName.trim().toUpperCase();
	}
}
