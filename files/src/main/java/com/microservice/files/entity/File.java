package com.microservice.files.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.microservice.files.dto.FileDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
// import lombok.AllArgsConstructor;
import lombok.Getter;
// import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table (name = "file")
// @AllArgsConstructor 
// @NoArgsConstructor
@Getter 
@Setter  
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "data_id", referencedColumnName = "id",  nullable = false)
    private Data data;
    
    @Column(length = 100, nullable = false)
    private String name;
    
    @Column(length = 64, nullable = false, unique = true)
    private String hash;

    @CreationTimestamp
    @Column(name = "created_at") 
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updateAt;
    
    public File(FileDto dto) {
        this.setName(dto.name());
        this.setHash(dto.hash());
        this.data.setMimetype(dto.mimetype());
        this.data.setDuration(dto.duration());
        this.data.setFileLength(dto.fileLength());
        this.data.setPhysicalPath(dto.physicalPath());
        this.data.setFileLength(dto.fileLength());
        this.data.setFilePath(dto.filePath());
        this.data.setCreatedFile(dto.createdFile());
    }
}
