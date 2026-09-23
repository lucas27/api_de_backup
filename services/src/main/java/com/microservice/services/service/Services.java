package com.microservice.services.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.services.client.ServicesClient;
import com.microservice.services.dto.FileDto;
import com.microservice.services.dto.UploadDto;

@Service 
public class Services {
    private ServicesClient client;

    public Services(ServicesClient client) {
        this.client = client;
    }

    public String uploadDataFile(
        MultipartFile file,
        Integer chunk,
        Integer totalChunk,
        UploadDto uploadDto
    ) {
        FileDto data = FileDto.createDataFile(uploadDto);
        String resp = client.storageFile(file, chunk, totalChunk, data);
        return resp;
    }
}
