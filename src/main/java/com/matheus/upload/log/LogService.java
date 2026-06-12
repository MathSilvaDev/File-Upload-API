package com.matheus.upload.log;

import com.matheus.upload.filemetadata.dto.response.FileMetadataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LogService {

    public void uploadFileLog(FileMetadataResponse dto){
        String message = String.format("Original Name: %s%nStored Name: %s%nSize: %d",
                dto.originalName(), dto.storedName(), dto.size());

        log.info(message);
    }
}
