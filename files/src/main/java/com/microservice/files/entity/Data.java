package com.microservice.files.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.microservice.files.enums.Mimetype;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "data")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter 
public class Data implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false)
    private Integer duration;

    @Column(name = "file_length", unique = false, nullable = false)
    private BigInteger fileLength;

    @Enumerated(EnumType.STRING)
    private Mimetype mimetype;

    @Column(name = "local_path", length = 100, unique = false, nullable = false)
    private String localPath;
    
    @Column(name = "file_path", length = 100, unique = false, nullable = false)
    private String filePath;
    
    @Column(name = "created_file", unique = true, nullable = false)
    private LocalDateTime createdFile;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at", updatable = true)
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "data")
    private FilesEntity file;
}
