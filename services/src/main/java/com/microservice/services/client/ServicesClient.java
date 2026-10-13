package com.microservice.services.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.services.configuration.FeignMultipartConfig;
import com.microservice.services.dto.FileDto;
import com.microservice.services.dto.StreamingFileDto;


@FeignClient(name="services-files", url = "${api.host}:8083/file", configuration = FeignMultipartConfig.class)
public interface ServicesClient {
    @PostMapping (value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String storageFile(
        @RequestPart("file") MultipartFile file,
        @RequestPart("chunkIndex") Integer chunk,
        @RequestPart("totalChunks") Integer total,
        @RequestPart("data") FileDto fileDto
    );

    @GetMapping(value="/files")
    List<StreamingFileDto> streamingFiles(
        @RequestParam("page") Integer page,
        @RequestParam("user") String userId,
        @RequestParam("userName") String userName
    );
}
