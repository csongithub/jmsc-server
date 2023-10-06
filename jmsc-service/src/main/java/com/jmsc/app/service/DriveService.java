/**
 * 
 */
package com.jmsc.app.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.common.dto.DirectoryDTO;
import com.jmsc.app.common.dto.FileMetaDataDTO;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.rqrs.GetFilesRequest;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.entity.Directory;
import com.jmsc.app.entity.FileMetaData;
import com.jmsc.app.repository.DirectoryRepository;
import com.jmsc.app.repository.FileMetaDataRepository;

/**
 * @author anuhr
 *
 */
@Service
public class DriveService {
	
	@Autowired
	private DirectoryRepository repository;
	
	@Autowired
	private FileMetaDataRepository fileRepositoty;
	
	
	public DirectoryDTO createDirectory(DirectoryDTO deDTO) throws Throwable {
		
		if(deDTO.getClientId() == null)
				throw new RuntimeException("Client id can be null");
		
		if(Strings.isNullOrEmpty(deDTO.getName()))
			throw new RuntimeException("Name can be null");
		
		if(Strings.isNullOrEmpty(deDTO.getDescription()))
			throw new RuntimeException("Description can not be null");
		

		Directory dEntity = ObjectMapperUtil.map(deDTO, Directory.class);
		Directory saved = repository.save(dEntity);
		DirectoryDTO dto = ObjectMapperUtil.map(saved, DirectoryDTO.class);
		return dto;
	}
	
	
	public List<DirectoryDTO> getAllDirectories(Long clientId){
		List<Directory> all = repository.findByClientId(clientId);
		
		if(Collections.isNullOrEmpty(all)) {
			return new ArrayList<DirectoryDTO>();
		}
		
		List<DirectoryDTO> allEntities = ObjectMapperUtil.mapAll(all, DirectoryDTO.class);
		return allEntities;
	}
	
	
	public DirectoryDTO getDirectory(Long clientId, Long directoryId){
		Optional<Directory> optional = repository.findByClientIdAndId(clientId, directoryId);
		
		if(!optional.isPresent()) {
			throw new RuntimeException("Directory not found");
		}
		
		DirectoryDTO directory = ObjectMapperUtil.map(optional.get(), DirectoryDTO.class);
		return directory;
	}
	
	
	public Integer deleteDirectory(Long clientId, Long id) {
		Optional<Directory> optional  = repository.findByClientIdAndId(clientId, id);
		
		if(optional.isPresent()) {
			Directory entity = optional.get();
			repository.delete(entity);
			return 0;
		} else {
			throw new ResourceNotFoundException("Category not found");
		}
	}
	
	
	public String storeFile(MultipartFile file) throws Exception {
		 String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	     if(fileName.contains(" ")){
	    	 fileName = fileName.replace(" ", "_");
	     }
		 try {
	        Path fileStorageLocation = Paths.get("C:\\Users\\\\anuhr\\Downloads\\jmsc\\").toAbsolutePath().normalize();
	        Path targetLocation =	fileStorageLocation.resolve(new Timestamp(System.currentTimeMillis()).getTime() + "_" + fileName);
	        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
	        return targetLocation.toString();
	     } catch (java.io.IOException ex) {
	    	 throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
	     }finally {
			
		}
	 }
	
	
	public List<FileMetaDataDTO> getAllFiles(GetFilesRequest req){
		List<FileMetaData> all = fileRepositoty.findAllByClientIdAndDirectoryIdAndSystemPath(req.getClientId(),
																							 req.getDirectoryId(),
																							 req.getSystemPath());
		List<FileMetaDataDTO> list= ObjectMapperUtil.mapAll(all, FileMetaDataDTO.class);
		return list;
	}
	
	
	public FileMetaDataDTO addFolder(FileMetaDataDTO dto) {
		FileMetaData entity = ObjectMapperUtil.map(dto, FileMetaData.class);
		FileMetaData file = fileRepositoty.save(entity);
		FileMetaDataDTO savedFile = ObjectMapperUtil.map(file, FileMetaDataDTO.class);
		
		return savedFile;
	}
	
	public FileMetaDataDTO uploadFile(FileMetaDataDTO dto) {
		
		//TODO: upload to aws s3 bucket
		FileMetaData entity = ObjectMapperUtil.map(dto, FileMetaData.class);
		FileMetaData file = fileRepositoty.save(entity);
		FileMetaDataDTO savedFile = ObjectMapperUtil.map(file, FileMetaDataDTO.class);
		
		return savedFile;
	}

}
