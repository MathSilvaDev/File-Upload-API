package com.matheus.upload.filemetadata.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileMetadataService {

    public void upload(MultipartFile file) throws IOException {

        Path destination = Paths.get("uploads")
                .resolve(file.getOriginalFilename());

        int count = 1;
        while (Files.exists(destination)){

            destination = Paths.get("uploads")
                    .resolve(file.getOriginalFilename()
                            + String.format(" (%s)", count));
            count++;
        }

        Files.copy(file.getInputStream(), destination);
    }

}
