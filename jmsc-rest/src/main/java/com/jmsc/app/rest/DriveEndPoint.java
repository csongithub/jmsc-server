/**
 * 
 */
package com.jmsc.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.jmsc.app.common.dto.MoveFilesRequest;
import com.jmsc.app.common.dto.RenameFileRequest;
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
	 public ResponseEntity<File> downloadFile(@PathVariable("client_id") Long clientId, 
			 									  @PathVariable("directory_id") Long directoryId,
			 									  @PathVariable("file_id") Long fileId)throws Throwable {
		
		File file  = service.downloadFile(clientId, directoryId, fileId);
		
		return ResponseEntity.ok(file);
	 }
	
	
	@DeleteMapping("/delete_file/{client_id}/{directory_id}/{file_id}")
	public ResponseEntity<Integer> deleteFile(@PathVariable("client_id") Long clientId, 
			  								  @PathVariable("directory_id") Long directoryId,
			  								  @PathVariable("file_id") Long fileId) {
		Integer statusCode = service.deleteFile(clientId, directoryId, fileId);
		return ResponseEntity.ok(statusCode);
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
	
	
	@PostMapping("/rename_file")
	public ResponseEntity<Boolean> rename(@RequestBody RenameFileRequest req)throws Throwable{
		Boolean response = service.rename(req);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/move_files")
	public ResponseEntity<Boolean> moveFiles(@RequestBody MoveFilesRequest req)throws Throwable{
		Boolean response = service.moveFiles(req);
		return ResponseEntity.ok(response);
	}
}
