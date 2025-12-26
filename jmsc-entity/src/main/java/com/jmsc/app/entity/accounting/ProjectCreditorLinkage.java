/**
 * 
 */
package com.jmsc.app.entity.accounting;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
