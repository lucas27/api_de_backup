package com.microservice.files.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.files.entity.FilesEntity;

public interface FileRepository extends JpaRepository<FilesEntity, Long> {

    
}