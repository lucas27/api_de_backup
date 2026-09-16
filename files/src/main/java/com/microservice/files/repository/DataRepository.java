package com.microservice.files.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.files.entity.Data;

public interface DataRepository extends JpaRepository<Data, Long>{
    
}
