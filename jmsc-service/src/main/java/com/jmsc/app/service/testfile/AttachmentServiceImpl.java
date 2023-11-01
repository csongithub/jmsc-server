package com.jmsc.app.service.testfile;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.entity.testfile.Attachment;

@Service
public class AttachmentServiceImpl implements AttachmentService{


    private static Map<String, Attachment> db = new HashMap<String, Attachment>();
    

    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception {
       String fileName = StringUtils.cleanPath(file.getOriginalFilename());
       try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                + fileName);
            }

            Attachment attachment= new Attachment(fileName,
            									  fileName,
            									  file.getContentType(),
            									  file.getBytes());
            db.put(fileName, attachment);
            
            return attachment;

       } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
       }
    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
    	Attachment attachment  = db.get(fileId);
    	return attachment;
    }
}
