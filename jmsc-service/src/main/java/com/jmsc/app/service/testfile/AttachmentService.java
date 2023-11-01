package com.jmsc.app.service.testfile;

import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.entity.testfile.Attachment;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file) throws Exception;

    Attachment getAttachment(String fileId) throws Exception;
}
