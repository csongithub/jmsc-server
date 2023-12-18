/**
 * 
 */
package com.jmsc.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.common.dto.DirectoryDTO;
import com.jmsc.app.common.dto.FileMetaDataDTO;
import com.jmsc.app.common.dto.MoveFilesRequest;
import com.jmsc.app.common.dto.RenameFileRequest;
import com.jmsc.app.common.enums.EFileType;
import com.jmsc.app.common.exception.ResourceNotFoundException;
import com.jmsc.app.common.rqrs.File;
import com.jmsc.app.common.rqrs.GetFilesRequest;
import com.jmsc.app.common.util.Collections;
import com.jmsc.app.common.util.ObjectMapperUtil;
import com.jmsc.app.common.util.Strings;
import com.jmsc.app.entity.Directory;
import com.jmsc.app.entity.FileMetaData;
import com.jmsc.app.entity.NoDataFileMetaData;
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
	
	
	private static final String MS_WORD_FILE_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
	private static final String MS_EXCEL_FILE_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private static final String MS_POWER_POINT_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
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
	        
	        String contentType = file.getContentType();
	        if(MS_WORD_FILE_CONTENT_TYPE.equalsIgnoreCase(contentType))
	        	contentType = "MS_WORD";
	        else if(MS_EXCEL_FILE_CONTENT_TYPE.equalsIgnoreCase(contentType))
	        	contentType = "MS_EXCEL";
	        else if(MS_POWER_POINT_CONTENT_TYPE.equals(contentType))
	        	contentType = "MS_PPT";
	        
	        entity.setContentType(contentType);
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
			String contentType = fileMetaData.getContentType();
			if("MS_WORD".equals(contentType))
				contentType = MS_WORD_FILE_CONTENT_TYPE;
			else if("MS_EXCEL".equals(contentType))
				contentType = MS_EXCEL_FILE_CONTENT_TYPE;
			else if("MS_PPT".equals(contentType))
				contentType = MS_POWER_POINT_CONTENT_TYPE;
			
			File file= new File(fileMetaData.getData(), 
								fileMetaData.getFileName(),
								contentType);
			
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
				List<NoDataFileMetaData> list = fileRepositoty.findAllByClientIdAndDirectoryIdAndSystemPath(clientId,
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
		List<NoDataFileMetaData> all = fileRepositoty.findAllByClientIdAndDirectoryIdAndSystemPath(req.getClientId(),
																							 req.getDirectoryId(),
																							 req.getSystemPath());
		List<FileMetaDataDTO> list= ObjectMapperUtil.mapAll(all, FileMetaDataDTO.class);
		return list;
	}
	
	
	public FileMetaDataDTO addFolder(FileMetaDataDTO dto) {
		
		List<NoDataFileMetaData> existing = fileRepositoty.findAllByClientIdAndSystemPathAndFileName(dto.getClientId(), dto.getSystemPath(), dto.getFileName());
		
		if(existing != null && existing.size() >0)
			throw new RuntimeException("Folder Already Exists");
		
		FileMetaData entity = ObjectMapperUtil.map(dto, FileMetaData.class);
		entity.setContentType("Folder");
		FileMetaData file = fileRepositoty.save(entity);
		FileMetaDataDTO savedFile = ObjectMapperUtil.map(file, FileMetaDataDTO.class);
		
		return savedFile;
	}
	
	public Boolean rename(RenameFileRequest req) {
		Optional<FileMetaData> optional = fileRepositoty.findByClientIdAndDirectoryIdAndId(req.getClientId(), req.getDirectoryId(), req.getFileId());
		if(optional.isPresent()) {
			FileMetaData file = optional.get();
			String fileExtention = null;
			
			if(!EFileType.DIRECTORY.equals(file.getFileType())) {
				fileExtention = file.getFileName().substring(file.getFileName().indexOf('.'));
				req.setNewName(req.getNewName() + fileExtention);
			}
			if(EFileType.DIRECTORY.equals(file.getFileType())) {
				String sysPath = file.getSystemPath();
				String oldName = file.getFileName();
				
				String oldPath = sysPath + "/" + oldName;
				String newPath = sysPath + "/" + req.getNewName();
				
				List<NoDataFileMetaData> allOtherFiles = fileRepositoty.findAllByClientId(req.getClientId());
				
				for(NoDataFileMetaData f: allOtherFiles) {
					if(f.getSystemPath().equals(oldPath)) {
						Optional<FileMetaData> optionalFile =  fileRepositoty.findByClientIdAndDirectoryIdAndId(f.getClientId(), f.getDirectoryId(), f.getId());
						optionalFile.get().setSystemPath(newPath);
//						otherFile.setSystemPath(newPath);
						fileRepositoty.save(optionalFile.get());
					} else if(f.getSystemPath().contains(oldPath+"/")) {
						Optional<FileMetaData> optionalFile =  fileRepositoty.findByClientIdAndDirectoryIdAndId(f.getClientId(), f.getDirectoryId(), f.getId());
						optionalFile.get().setSystemPath(f.getSystemPath().replace(oldPath + "/", newPath + "/"));
//						otherFile.setSystemPath(otherFile.getSystemPath().replace(oldPath + "/", newPath + "/"));
						fileRepositoty.save(optionalFile.get());
					}
				}
				
			}
			file.setFileName(req.getNewName());
			file.setDescription(req.getNewDescription());
			fileRepositoty.save(file);
		} else {
			throw new ResourceNotFoundException("File not found");
		}
		return Boolean.TRUE;
	}
	
	
	public Boolean moveFiles(MoveFilesRequest req) {
		
		if(Collections.isNullOrEmpty(req.getFiles()))
			throw new RuntimeException("Invalid Request");
		
		for(Long fileId: req.getFiles()) {
			Optional<FileMetaData> optional = fileRepositoty.findByClientIdAndDirectoryIdAndId(req.getClientId(), req.getDirectoryId(), fileId);
			if(optional.isPresent()) {
				FileMetaData file = optional.get();
				if(EFileType.DIRECTORY.equals(file.getFileType())) {
					
					List<NoDataFileMetaData> all = fileRepositoty.findAllByClientIdAndDirectoryIdAndSystemPath(req.getClientId(),
							 																				   req.getDirectoryId(),
							 																				   file.getSystemPath() + "/" + file.getFileName());
					//First move all files under directory
					if(Collections.isNotNullOrEmpty(all)) {
						List<Long> fileIds =  all.stream().map(f-> f.getId()).collect(Collectors.toList());
						
						MoveFilesRequest newReq = new MoveFilesRequest();
						newReq.setClientId(req.getClientId());
						newReq.setDirectoryId(req.getDirectoryId());
						newReq.setFiles(fileIds);
						newReq.setNewPath(req.getNewPath() + "/" + file.getFileName());
						
						moveFiles(newReq);
						
						//Move Directory
//						file.setSystemPath(req.getNewPath());
//						fileRepositoty.save(file);
					}
				}
				file.setSystemPath(req.getNewPath());
				fileRepositoty.save(file);
			}
		}
		return Boolean.TRUE;
	}
}
