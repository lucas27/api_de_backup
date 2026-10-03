package com.microservice.files.entity;

import java.time.LocalDateTime;
import java.util.UUID;

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
public class FilesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false, unique = false)
    private String user;
    
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
    
    public static FilesEntity saveFile(FileDto dto) {
        FilesEntity file = new FilesEntity();
        
        file.setName(dto.name());
        file.setHash(UUID.randomUUID().toString());
        file.setUser(dto.userId());

        Data data = new Data();

        data.setMimetype(dto.mimetype());
        data.setDuration(dto.duration());
        data.setFileLength(dto.fileLength());
        // this.data.setPhysicalPath(dto.physicalPath());
        data.setFileLength(dto.fileLength());
        data.setFilePath(dto.filePath());
        data.setCreatedFile(dto.createdFile());
        
        file.setData(data);

        return file;
    }
}
