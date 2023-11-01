/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.common.dto.DirectoryDTO;
import com.jmsc.app.common.dto.FileMetaDataDTO;
import com.jmsc.app.common.rqrs.File;
import com.jmsc.app.common.rqrs.GetFilesRequest;
import com.jmsc.app.service.DriveService;

import io.swagger.annotations.Api;

/**
 * @author anuhr
 *
 */
@RestController
@RequestMapping("/v1/drive")
@Api(value = "APIs to handle site's data")
public class DriveEndPoint {
	
	@Autowired
	private DriveService service;
	
	
	@PostMapping("/create_directory")
	public ResponseEntity<DirectoryDTO> createDirectory(@RequestBody DirectoryDTO deDTO)throws Throwable{
		DirectoryDTO entity = service.createDirectory(deDTO);
		return ResponseEntity.ok(entity);
	}
	
	
	
	@GetMapping("/all/{client_id}")
	public ResponseEntity<List<DirectoryDTO>> getAll(@PathVariable("client_id") Long clientId){
		List<DirectoryDTO> all = service.getAllDirectories(clientId);
		return ResponseEntity.ok(all);
	}
	
	
	@GetMapping("/directory/{client_id}/{directory_id}")
	public ResponseEntity<DirectoryDTO> getAll(@PathVariable("client_id") Long clientId, @PathVariable("directory_id")Long directoryId){
		DirectoryDTO response = service.getDirectory(clientId, directoryId);
		return ResponseEntity.ok(response);
	}
	
	
	
	@DeleteMapping("/delete/{client_id}/{directory_id}")
	public ResponseEntity<Integer> deleteDirectory(@PathVariable("client_id")Long clientId,
												 @PathVariable("directory_id")Long directoryId) {
		Integer statusCode = service.deleteDirectory(clientId, directoryId);
		return ResponseEntity.ok(statusCode);
	}
	
	
	@PostMapping("/upload_file")
	 public ResponseEntity<FileMetaDataDTO> uploadFile(@RequestParam("file") MultipartFile file, 
			 				  @RequestParam("metadata")String jsonMetadata)throws Throwable {
		FileMetaDataDTO response = service.uploadFile(file, jsonMetadata);
		return ResponseEntity.ok(response);
	 }
	
	
	@GetMapping("/download_file/{client_id}/{directory_id}/{file_id}")
	 public ResponseEntity<Resource> downloadFile(@PathVariable("client_id") Long clientId, 
			 									  @PathVariable("directory_id") Long directoryId,
			 									  @PathVariable("file_id") Long fileId,
			 									 HttpServletResponse response)throws Throwable {
		
		File file  = service.downloadFile(clientId, directoryId, fileId);
		
//		HttpHeaders headers = new HttpHeaders();
//	    headers.setContentType(MediaType.parseMediaType(file.getContentType()));
//	    response.setHeader("Content-Disposition", "attachment; filename=" + file.getFileName());
//	    return new HttpEntity<byte[]>(file.getData(), headers);
		
		 ByteArrayResource resource = new ByteArrayResource(file.getData());
		 return ResponseEntity.ok()
		            .contentType(MediaType.APPLICATION_OCTET_STREAM)
		            .contentLength(resource.contentLength())
		            .header(HttpHeaders.CONTENT_DISPOSITION,
		                    ContentDisposition.attachment()
		                        .filename(file.getFileName())
		                        .build().toString())
		            .body(resource);
		
//		return  ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(file.getContentType()))
//                .contentLength(file.getData().length)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
//                .body(new ByteArrayResource(file.getData()));
	 }
	
	
	
	@PostMapping("/create_folder")
	 public ResponseEntity<FileMetaDataDTO> addFolder(@RequestBody FileMetaDataDTO dto)throws Throwable {
		FileMetaDataDTO response = service.addFolder(dto);
		return ResponseEntity.ok(response);
	 }
	
	
	@PostMapping("/list_files")
	 public ResponseEntity<List<FileMetaDataDTO>> listFiles(@RequestBody GetFilesRequest request)throws Throwable {
		List<FileMetaDataDTO> response =  service.listFiles(request);
		return ResponseEntity.ok(response);
	 }
	
}
