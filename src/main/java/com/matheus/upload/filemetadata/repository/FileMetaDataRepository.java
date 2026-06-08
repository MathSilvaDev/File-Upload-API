package com.matheus.upload.filemetadata.repository;

import com.matheus.upload.filemetadata.entity.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetaData, Long> {
}
