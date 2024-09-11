/**
 * 
 */
package com.jmsc.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.common.dto.BankGuaranteeDTO;
import com.jmsc.app.common.rqrs.File;
import com.jmsc.app.service.BankGuaranteeService;

import io.swagger.annotations.Api;

/**
 * @author anuhr
 *
 */
@RestController
@RequestMapping("/v1/file_upload_download")
@Api(value = "APIs to loans")
public class FileUploadDownloadEndPoint {
	
	@Autowired
	private BankGuaranteeService bgService;
	
	
	
	@PostMapping("/upload/{client_id}/{file_type}/{file_id}")
	 public ResponseEntity<BankGuaranteeDTO> uploadFile(@RequestParam("file") MultipartFile file, 
			 										   @PathVariable("client_id") Long clientId, 
			 										   @PathVariable("file_type") String filType,
			 										   @PathVariable("file_id") Long fileId)throws Throwable {
		BankGuaranteeDTO response = bgService.upload(file, clientId, fileId);
		return ResponseEntity.ok(response);
	 }
	
	@GetMapping("/hello/{client_id}/{file_type}/{file_id}")
	 public ResponseEntity<String> uploadFile(@PathVariable("client_id") Long clientId, 
			 										   @PathVariable("file_type") String filType,
			 										   @PathVariable("file_id") Long fileId)throws Throwable {
		
		return ResponseEntity.ok("Hello");
	 }
	
	
	@GetMapping("/download/{client_id}/{file_type}/{file_id}")
	 public ResponseEntity<File> downloadFile(@PathVariable("client_id") Long clientId, 
			 								  @PathVariable("file_type") String filType,
			 							      @PathVariable("file_id") Long fileId)throws Throwable {
		
		File file = null;
		if(filType.equals("BANK_GUARANTEE")) {
			file = bgService.download(clientId, fileId);
		}
		
		return ResponseEntity.ok(file);
	 }

}
