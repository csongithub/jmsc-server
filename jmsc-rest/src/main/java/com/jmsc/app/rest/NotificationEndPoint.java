/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmsc.app.common.dto.NotificationDTO;
import com.jmsc.app.service.NotificationService;

import io.swagger.annotations.Api;

/**
 * @author chandan
 *
 */
@RestController
@RequestMapping("/v1/notification")
@Api(value = "APIs to notification")
public class NotificationEndPoint {
	
	
	@GetMapping("/all/{client_id}")
	public ResponseEntity<List<NotificationDTO>> all(@PathVariable("client_id") Long clientId){
		List<NotificationDTO> list = NotificationService.getNotifications(clientId);
		return ResponseEntity.ok(list);
	}
	
	
	
	@GetMapping("/refresh_and_get/{client_id}")
	public ResponseEntity<List<NotificationDTO>> refreshAndGet(@PathVariable("client_id") Long clientId){
		List<NotificationDTO> response = NotificationService.refreshAndGet(clientId);
		return ResponseEntity.ok(response);
	}

}
