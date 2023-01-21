package com.server.filesystem.db.repository;

import com.server.filesystem.db.model.StorageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageFileRepository extends JpaRepository<StorageFile, Long> {
}
