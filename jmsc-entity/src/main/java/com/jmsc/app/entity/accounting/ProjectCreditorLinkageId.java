/**
 * 
 */
package com.jmsc.app.entity.accounting;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.jetbrains.annotations.NotNull;

/**
 * @author anuhr
 *
 */
@Embeddable
public class ProjectCreditorLinkageId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2341777412918911457L;
	
	
	@NotNull
	@Column(name = "CLIENT_ID")
	private Long clientId;
	
	@NotNull
	@Column(name = "PROJECT_ID")
	private Long projectId;
	
	@NotNull
	@Column(name = "CREDITOR_ID")
	private Long creditorId;
	
	@NotNull
	@Column(name = "LEDGER_ID")
	private Long ledgerId;

	public ProjectCreditorLinkageId() {
		
	}

	public ProjectCreditorLinkageId(Long clientId, Long projectId, Long creditorId, Long ledgerId) {
		
		this.clientId = clientId;
		this.projectId = projectId;
		this.creditorId = creditorId;
		this.ledgerId = ledgerId;
	}
	
	 @Override
	 public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof ProjectCreditorLinkageId)) return false;
	        ProjectCreditorLinkageId that = (ProjectCreditorLinkageId) o;
	        return Objects.equals(clientId, that.clientId)
	        		&& Objects.equals(projectId, that.projectId)
	        		&& Objects.equals(creditorId, that.creditorId)
	                && Objects.equals(ledgerId, that.ledgerId);
	 }

	 @Override
	 public int hashCode() {
		 return Objects.hash(clientId,projectId,creditorId, ledgerId);
	 }
	
	
	
}
