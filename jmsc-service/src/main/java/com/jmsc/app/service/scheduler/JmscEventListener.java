/**
 * 
 */
package com.jmsc.app.service.scheduler;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.jmsc.app.config.jmsc.JmscEvent;
import com.jmsc.app.config.jmsc.ServiceLocator;

/**
 * @author Chandan
 *
 */
@Component
public class JmscEventListener implements ApplicationListener<JmscEvent> {

	
	
	@Override
	public void onApplicationEvent(JmscEvent event) {
		System.out.println("Received Jmsc event...");
		if(JmscEvent.POST_ACTIVATE_EVENT_TOPIC.equals(event.getTopic())) {
			System.out.println("Processing post activate event");
			PostActivationService postActivateService = ServiceLocator.getService(PostActivationService.class);
			try {
				postActivateService.dataBackup();
				postActivateService.markFacilityExpired();
				postActivateService.evaluateMachineExpiry();
				postActivateService.markBGExpired();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		
	}

}
