/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jmsc.app.common.dto.NotificationDTO;
import com.jmsc.app.config.jmsc.ServiceLocator;

/**
 * @author chandan
 *
 */
public class NotificationService {

	private static Map<Long, List<NotificationDTO>> notificationsMap = new ConcurrentHashMap<>();
	
	
	public static void pushNotification(Long clientId, NotificationDTO notification) {
		if(notificationsMap.containsKey(clientId)) {
			List<NotificationDTO> notifications = notificationsMap.get(clientId);
			notifications.add(notification);
			notificationsMap.put(clientId, notifications);
		} else {
			List<NotificationDTO> notifications = new ArrayList<NotificationDTO>();
			notifications.add(notification);
			notificationsMap.put(clientId, notifications);
		}
	}
	
	
	
	public static void pushNotifications(Long clientId, List<NotificationDTO> upcomingNotifications) {
		if(notificationsMap.containsKey(clientId)) {
			List<NotificationDTO> notifications = notificationsMap.get(clientId);
			notifications.addAll(upcomingNotifications);
			notificationsMap.put(clientId, notifications);
		} else {
			List<NotificationDTO> notifications = new ArrayList<NotificationDTO>();
			notifications.addAll(upcomingNotifications);
			notificationsMap.put(clientId, notifications);
		}
	}
	
	
	public static List<NotificationDTO> getNotifications(Long clientId){
		if(notificationsMap.containsKey(clientId)) {
			return notificationsMap.get(clientId);
		} else {
			return new ArrayList<>();
		}
	}



	public static List<NotificationDTO> refreshAndGet(Long clientId) {
		if(notificationsMap.containsKey(clientId)) {
			notificationsMap.remove(clientId);
		}
		
		List<NotificationDTO> all = ServiceLocator.getService(MachineService.class).evaluateExpiryForClient(clientId);
		return all;
	}
	
	
	public static int count(Long clientId) {
		if(notificationsMap.containsKey(clientId)) {
			return notificationsMap.get(clientId).size();
		} else {
			return 0;
		}
	}
	
}
