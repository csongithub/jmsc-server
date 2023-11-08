package com.jmsc.app.rest.testfile;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jmsc.app.common.testfile.ResponseData;
import com.jmsc.app.entity.testfile.Attachment;
import com.jmsc.app.service.testfile.AttachmentService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/v1/file")
@Api(value = "APIs to handle site's data")
public class AttachmentController {

    private AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
        Attachment attachment = null;
        String downloadURl = "";
        attachment = attachmentService.saveAttachment(file);

        return new ResponseData(attachment.getFileName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile() throws Exception {
    	String fileId = "1";
        Attachment file = null;
        file = attachmentService.getAttachment(fileId);
        
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"Template.pdf\"";
        
//        return  ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(file.getFileType()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
//                .body(new ByteArrayResource(file.getData()));
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(new ByteArrayResource(file.getData()));
    }
}
