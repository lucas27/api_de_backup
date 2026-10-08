package com.microservice.files.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.microservice.files.entity.FilesEntity;

public interface FileRepository extends JpaRepository<FilesEntity, Long> {
    @Query("""
            SELECT f FROM FilesEntity f WHERE user = :userId
            """)
    Page<FilesEntity> findAllByUserId(@Param("userId") Long id, Pageable pageable);
    
}