package com.example.controllers;

import com.example.data.vo.v1.UploadFileResponseVO;
import com.example.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.logging.Logger;

@Tag(name = "File Endpoint")
@RequestMapping("/api/file/v1")
@RestController
public class FileController {

    @Autowired
    private FileStorageService service;

    private final Logger logger = Logger.getLogger(FileController.class.getName());

    @PostMapping("/uploadFile")
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("Storing file to disk");

        var filename = service.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/file/v1/downloadFile/")
            .path(filename)
            .toUriString();

        return new UploadFileResponseVO(filename, fileDownloadUri, file.getContentType(), file.getSize());
    }

}
