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
import com.jmsc.app.service.EInvoiceService;

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
	
	@Autowired
	private EInvoiceService eInvoiceService;
	
	
	
	@PostMapping("/upload/{client_id}/{file_type}/{file_id}")
	 public ResponseEntity<Boolean> uploadFile(@RequestParam("file") MultipartFile file, 
			 										   @PathVariable("client_id") Long clientId, 
			 										   @PathVariable("file_type") String filType,
			 										   @PathVariable("file_id") Long fileId)throws Throwable {
		
		if(filType.equals("BANK_GUARANTEE")) {
			bgService.upload(file, clientId, fileId);
		} else if(filType.equals("EINVOICE_MEMO")) {
			eInvoiceService.upload(file, clientId, fileId, "memo");
		}else if(filType.equals("EINVOICE")) {
			eInvoiceService.upload(file, clientId, fileId, "invoice");
		}
		return ResponseEntity.ok(Boolean.TRUE);
	 }
	

	
	@GetMapping("/download/{client_id}/{file_type}/{file_id}")
	 public ResponseEntity<File> downloadFile(@PathVariable("client_id") Long clientId, 
			 								  @PathVariable("file_type") String filType,
			 							      @PathVariable("file_id") Long fileId)throws Throwable {
		
		File file = null;
		if(filType.equals("BANK_GUARANTEE")) {
			file = bgService.download(clientId, fileId);
		} else if(filType.equals("EINVOICE_MEMO")) {
			file = eInvoiceService.download(clientId, fileId, "memo");
		} else if(filType.equals("EINVOICE")) {
			file = eInvoiceService.download(clientId, fileId, "invoice");
		}
		
		return ResponseEntity.ok(file);
	 }

}
