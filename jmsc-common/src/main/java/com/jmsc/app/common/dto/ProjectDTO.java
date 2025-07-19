/**
 * 
 */
package com.jmsc.app.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author anuhr
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectDTO extends BaseDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7266231461130457489L;

	private Long id;
	
	private String nickName;
	
	private String fullName;
	
	private String packageNo;
	
	private String agreementNo;
	
	private Date agreementDate;
	
	private Double agreementAmount;

	private Double constructionAmount;
	
	private Double maintenanceAmount;
	
	private Double otherAmount;
	
	private Double securityAmount;
}
