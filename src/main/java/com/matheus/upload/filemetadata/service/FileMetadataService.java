package com.matheus.upload.filemetadata.service;

import com.matheus.upload.filemetadata.dto.response.FileMetadataResponse;
import com.matheus.upload.filemetadata.entity.FileMetadata;
import com.matheus.upload.filemetadata.repository.FileMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class FileMetadataService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private final FileMetadataRepository fileMetadataRepository;

    public FileMetadataResponse upload(MultipartFile file) throws IOException {

        Path uploads = Paths.get("uploads");

        if(Files.notExists(uploads)){
            Files.createDirectory(uploads);
        }

        Path destination = uploads.resolve(file.getOriginalFilename());

        int count = 1;
        while (Files.exists(destination)){
            count++;

            int dotIndex = file.getOriginalFilename().lastIndexOf('.');

            String baseName = file.getOriginalFilename().substring(0, dotIndex);
            String extension = file.getOriginalFilename().substring(dotIndex);

            destination = uploads.resolve(String.format(
                    "%s(%d)%s", baseName, count, extension));
        }

        Files.copy(file.getInputStream(), destination);

        FileMetadataResponse response = new FileMetadataResponse(
                null,
                file.getOriginalFilename(),
                destination.getFileName().toString(),
                file.getSize(),
                null
        );

        FileMetadata fileMetadata = saveMultiPartfile(response);

        String json = objectMapper.writeValueAsString(fileMetadata);

        kafkaTemplate.send("uploaded-file", json);

        System.out.println("=================================");
        System.out.println("======UPLOADED SUCCESSFULLY======");
        System.out.println("=================================");

        return toResponse(fileMetadata);
    }

    private FileMetadata saveMultiPartfile(FileMetadataResponse response){

        FileMetadata fileMetadata = new FileMetadata(
                response.originalName(),
                response.storedName(),
                response.size()
        );

        return fileMetadataRepository.save(fileMetadata);
    }

    private FileMetadataResponse toResponse(FileMetadata fileMetadata){
        return new FileMetadataResponse(
                fileMetadata.getId(),
                fileMetadata.getOriginalName(),
                fileMetadata.getStoredName(),
                fileMetadata.getSize(),
                fileMetadata.getUploadDate()
        );
    }

}
