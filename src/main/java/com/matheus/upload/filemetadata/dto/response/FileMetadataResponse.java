package com.matheus.upload.filemetadata.dto.response;

import java.time.Instant;

public record FileMetadataResponse(
        Long id,
        String originalName,
        String storedName,
        Long size,
        Instant uploadDate
) { }
