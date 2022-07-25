/**
 * 
 */
package com.jmsc.app.config.jmsc;

import org.springframework.context.ApplicationEvent;

/**
 * @author Chandan
 *
 */
public class JmscEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4350934072839621251L;
	public static final String POST_ACTIVATE_EVENT_TOPIC = "POST_ACTIVATE_EVENT_TOPIC";

	private String topic;
	
	private Object payload;
	
	
	
	
	public JmscEvent(Object source, String topic, Object payload) {
        super(source);
        this.topic = topic;
        this.payload = payload;
    }


	public Object getPayload() {
		return payload;
	}



	public String getTopic() {
		return topic;
	}

	    
}
