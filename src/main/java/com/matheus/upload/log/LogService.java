package com.matheus.upload.log;

import com.matheus.upload.filemetadata.dto.response.FileUploadEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "uploaded-file", groupId = "log-group")
    public void uploadFileLog(FileUploadEvent event){

        String message = String.format("%n==========%n" +
                        "INFO: Original Name: %s%nStored Name: %s%nSize: %d" +
                        "%n==========",
                event.originalName(), event.storedName(), event.size());

        log.info(message);
    }
}
