package com.matheus.upload.filemetadata.controller;

import com.matheus.upload.filemetadata.service.FileMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileMetadataController {

    private final FileMetadataService fileMetadataService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upload(
            @RequestParam("file") MultipartFile file) throws IOException {

        fileMetadataService.upload(file);

        return ResponseEntity.ok().build();
    }
}
