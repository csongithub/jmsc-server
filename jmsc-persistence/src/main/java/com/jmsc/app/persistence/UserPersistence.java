/**
 * 
 */
package com.jmsc.app.persistence;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

/**
 * @author chandan
 *
 */
@Component
public class UserPersistence extends AbstractPersistence {

	@PostConstruct
	public void init() {
		System.out.println("Got EntityManager Reference..: " + em.getProperties());
	}
}
