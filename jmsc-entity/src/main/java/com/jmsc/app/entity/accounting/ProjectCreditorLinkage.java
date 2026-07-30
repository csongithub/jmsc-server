/**
 * 
 */
package com.jmsc.app.entity.accounting;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Data;

/**
 * @author anuhr
 *
 */
@Data
@Entity
@Table(name = "PROJECT_CREDITOR_LINKAGE")
public class ProjectCreditorLinkage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -337615493545742711L;
	
	@EmbeddedId
	ProjectCreditorLinkageId id;
}
