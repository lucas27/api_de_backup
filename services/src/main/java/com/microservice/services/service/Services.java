package com.microservice.services.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.services.client.ServicesClient;
import com.microservice.services.client.UserClient;
import com.microservice.services.dto.FileDto;
import com.microservice.services.dto.UploadDto;
import com.microservice.services.dto.UserDto;

@Service 
public class Services {
    private final ServicesClient servicesClient;
    private final UserClient userClient;

    public Services(ServicesClient client, UserClient userClient) {
        this.servicesClient = client;
        this.userClient = userClient;
    }

    public String uploadDataFile(
        String id,
        MultipartFile file,
        Integer chunk,
        Integer totalChunk,
        UploadDto uploadDto
    ) {
        UserDto userDto = userClient.getUserName(id);
        FileDto data = FileDto.createDataFile(uploadDto, id, userDto.name());
        String resp = servicesClient.storageFile(file, chunk, totalChunk, data);
        return resp;
    }
}
