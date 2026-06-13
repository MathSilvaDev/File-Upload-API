package com.matheus.upload.filemetadata.dto.response;

public record FileUploadEvent(
    String originalName,
    String storedName,
    Long size
){}
