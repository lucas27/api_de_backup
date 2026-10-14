package com.microservice.files.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.files.dto.request.FileDto;
import com.microservice.files.dto.response.StreamingFileDto;
import com.microservice.files.entity.FilesEntity;
import com.microservice.files.utils.FilesComponent;
import com.microservice.files.utils.FolderComponent;
import com.microservice.files.utils.DataBaseComponent;

@Service
public class FilesService {

    private final FolderComponent folderComponent;

    private final FilesComponent filesComponent;

    private final DataBaseComponent dataBaseComponent;

    // foi a melhor forma de resolver o problema do async, ele vai adicionar o número de chunks.
    private static ArrayList<Integer> countIndex = new ArrayList<>();

    public FilesService(FolderComponent folderComponent, FilesComponent filesComponent,
        DataBaseComponent dataBaseComponent) {
        // evitar o erro de byte ao inicializar
        super();
        this.folderComponent = folderComponent;
        this.filesComponent = filesComponent;
        this.dataBaseComponent = dataBaseComponent;
    }

    @Async
    public CompletableFuture<String> uploadFiles(MultipartFile file, Integer chunkIndex, Integer totalChunk, FileDto dto) throws IllegalStateException, IOException {
        String resp = null;

        Map<String, Path> folders = folderComponent.createFolder(totalChunk, dto);

        Path tempFolderPath = folders.get("temp folder");

        String definitivePath = folders.get("user folder").toString();

        resp = filesComponent.createChunkFiles(file, tempFolderPath, chunkIndex, totalChunk, dto);
        
        countIndex.add(chunkIndex);

        if(countIndex.size() == totalChunk) {
            resp = filesComponent.mergeChunkFiles(tempFolderPath.toString(), definitivePath, dto);
            
            dataBaseComponent.saveDataBase(dto, definitivePath);

            createImagesMiniature(dto.userName());

            // para limpar lista no final.
            countIndex.clear();
        }
         
        return CompletableFuture.completedFuture(
            resp 
        );
    }

    public void createImagesMiniature(String userName) throws IOException {

        // retorna em string o caminho das pastas do usuário e da miniatura.
        Map<String, String> folder = folderComponent.createMiniatureFolder(userName);

        filesComponent.testeFFMPEG(folder.get("miniature"), folder.get("user"));
    }

    public List<StreamingFileDto> streamingFiles(Integer page, Long userId, String userName) throws IOException {
        Map<String, String> folder = folderComponent.createMiniatureFolder(userName);
    
        // não gosto da ideia, mas foi o que deu certo dentro de uma lista de arquivos,
        // caso contrario, eu teria que fazer separado para cada arquivo e dto ou criar um zip
        String miniatureFile = filesComponent.downloadFile(folder.get("miniature"));
 
        Page<FilesEntity> files = dataBaseComponent.fileData(page, userId);
        List<StreamingFileDto> dto = files.stream()
            .map(file -> {
                return new StreamingFileDto(file, miniatureFile);
            })
            .toList();
        return dto;
    }
}
