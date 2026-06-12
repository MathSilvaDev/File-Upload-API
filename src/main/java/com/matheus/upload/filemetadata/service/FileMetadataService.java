package com.matheus.upload.filemetadata.service;

import com.matheus.upload.filemetadata.dto.response.FileMetadataResponse;
import com.matheus.upload.filemetadata.entity.FileMetadata;
import com.matheus.upload.filemetadata.repository.FileMetadataRepository;
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

    private final FileMetadataRepository fileMetadataRepository;

    public FileMetadataResponse upload(MultipartFile file) throws IOException {

        Path destination = Paths.get("uploads")
                .resolve(file.getOriginalFilename());

        int count = 1;
        while (Files.exists(destination)){
            count++;

            int dotIndex = file.getOriginalFilename().lastIndexOf('.');

            String baseName = file.getOriginalFilename().substring(0, dotIndex);
            String extension = file.getOriginalFilename().substring(dotIndex);

            destination = Paths.get("uploads")
                    .resolve(String.format(
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
