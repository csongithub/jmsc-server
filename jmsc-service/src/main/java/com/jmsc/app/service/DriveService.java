/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.common.dto.DirectoryDTO;
import com.jmsc.app.common.dto.FileMetaDataDTO;
import com.jmsc.app.common.enums.EFileType;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.rqrs.File;
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
	
	
//	@Autowired
//	private AwsConfig aws;
	
//	@Autowired
//	private AmazonS3Service amazonS3Service;
	
	
//	private static Map<Long, FileMetaData> db = new HashMap<Long, FileMetaData>();
	
	
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
			throw new ResourceNotFoundException("Directory not found");
		}
	}
	
	
	public FileMetaDataDTO uploadFile(MultipartFile file, String jsonMetadata) throws Exception {
		FileMetaDataDTO fileMetaData = ObjectMapperUtil.object(jsonMetadata, FileMetaDataDTO.class);
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		fileMetaData.setFileName(fileName);
		
	    if (file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file");
		try {   
	        Map<String, String> metadata = new HashMap<>();
	        metadata.put("Content-Type", file.getContentType());
	        metadata.put("Content-Length", String.valueOf(file.getSize()));
	        
//	        String filePath = String.format("%s/%s/%s", aws.getBucketName(),fileMetaData.getClientId(), fileMetaData.getSystemPath());
	      
//	        PutObjectResult putResult = amazonS3Service.upload(filePath, fileName, Optional.of(metadata), file.getInputStream());
	        
	        FileMetaData entity = ObjectMapperUtil.map(fileMetaData, FileMetaData.class);
	        entity.setFilePath(entity.getSystemPath()); //Here AWS path needs to be set from line no 124
	        entity.setData(file.getBytes());
	        entity.setContentType(file.getContentType());
			FileMetaData savedFile = fileRepositoty.save(entity);
//	        entity.setId(1l);
//	        db.put(1l, entity);
			FileMetaDataDTO response = ObjectMapperUtil.map(savedFile, FileMetaDataDTO.class);
	        return response;
	     } catch (java.io.IOException ex) {
	    	 throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
	     }finally {
			
		}
	 }
	
	
	
	public File downloadFile(Long clientId, Long directoryId, Long fileId)throws Exception {
		Optional<FileMetaData> optional = fileRepositoty.findByClientIdAndDirectoryIdAndId(clientId, directoryId, fileId);
//		FileMetaData f = db.get(1l);
		if(optional.isPresent()) {
//			S3Object file = amazonS3Service.download(optional.get().getFilePath(), optional.get().getFileName());
//			S3ObjectInputStream is = file.getObjectContent();
//			byte[] content = IOUtils.toByteArray(is);
			FileMetaData fileMetaData = optional.get();
			File file= new File(fileMetaData.getData(), 
								fileMetaData.getFileName(),
								fileMetaData.getContentType());
			
			return file;
		} else {
			throw new ResourceNotFoundException("Selected file does not exist in system.");
		}
	}
	
	
	public Integer deleteFile(Long clientId, Long directoryId, Long fileId) {
		Optional<FileMetaData> optional = fileRepositoty.findByClientIdAndDirectoryIdAndId(clientId, directoryId, fileId);
		if(optional.isPresent()) {
			FileMetaData file = optional.get();
			if(EFileType.DIRECTORY.equals(file.getFileType())) {
				//Check if the directory is empty, then delete otherwise throw error
				String systemPath = file.getSystemPath() + "/" + file.getFileName();
				List<FileMetaData> list = fileRepositoty.findAllByClientIdAndDirectoryIdAndSystemPath(clientId,
																									 directoryId,
																									 systemPath);
				if(list.isEmpty()) {
					fileRepositoty.delete(optional.get());
					return 0;
				}else {
					throw new RuntimeException("Folder is not empty");
				}
			} else {
				fileRepositoty.delete(optional.get());
				return 0;
			}
		} else {
			throw new ResourceNotFoundException("File not found");
		}
	}
	
	
	public List<FileMetaDataDTO> listFiles(GetFilesRequest req){
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
	

}
