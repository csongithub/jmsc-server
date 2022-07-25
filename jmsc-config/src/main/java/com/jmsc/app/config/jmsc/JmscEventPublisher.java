/**
 * 
 */
package com.jmsc.app.config.jmsc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author Chandan
 *
 */
@Component
public class JmscEventPublisher {
	
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	public void publishEvent(final Map<String, Object> eventMap) {
		
		String topic =  (String)eventMap.get("topic");
		Object payload = eventMap.get("payload");
		
        JmscEvent event = new JmscEvent(this, topic, payload);
        eventPublisher.publishEvent(event);
    }

}
