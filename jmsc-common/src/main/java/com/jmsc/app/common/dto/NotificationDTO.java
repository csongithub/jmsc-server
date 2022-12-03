package com.jmsc.app.common.dto;

import java.io.Serializable;

import com.jmsc.app.common.enums.ENotificationEntityType;

import lombok.Data;

@Data
public class NotificationDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4326911485501524214L;
	
	
	private Long clientId;
	
	private Long entityId;
	
	private ENotificationEntityType entityType;
	
	private MachineDTO machine;
	
	private String notificationMessage;

}
