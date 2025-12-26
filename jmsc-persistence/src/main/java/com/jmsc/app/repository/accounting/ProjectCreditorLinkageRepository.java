/**
 * 
 */
package com.jmsc.app.repository.accounting;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmsc.app.entity.accounting.ProjectCreditorLinkage;
import com.jmsc.app.entity.accounting.ProjectCreditorLinkageId;

/**
 * @author anuhr
 *
 */
public interface ProjectCreditorLinkageRepository
		extends JpaRepository<ProjectCreditorLinkage, ProjectCreditorLinkageId> {

}
