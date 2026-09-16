package com.microservice.files.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.files.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {

    
}