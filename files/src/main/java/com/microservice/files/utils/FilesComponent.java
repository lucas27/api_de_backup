package com.microservice.files.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.microservice.files.dto.request.FileDto;
import com.microservice.files.enums.Mimetype;

import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

@Component
@RequiredArgsConstructor   
public class FilesComponent {
    @Value("${api.ffmpeg-absolute-path}")
    private String ffmpegPath; 

    public String createChunkFiles(MultipartFile file, Path folderPath, Integer chunkIndex, Integer totalChunk, FileDto dto) throws IllegalStateException, IOException {

        String fileName = dto.name() + "_part_" + chunkIndex + "." + dto.mimetype().toString().toLowerCase();
        
        Path filePath = folderPath.resolve(fileName);

        if(file.isEmpty()) {
            throw new RuntimeException("arquivo vázio");
        }else if(Files.exists(filePath)) {
            throw new RuntimeException("O arquivo já existe");
        }
        
        file.transferTo(filePath.toFile());
        
        return "chunk criado com sucesso"; 
    }

    public String mergeChunkFiles(String tempFolderPath, String definitivePath, FileDto dto) throws IOException {
        File chunkFiles = new File(tempFolderPath);

        File[] fileNames = chunkFiles.listFiles((dir, name) -> name.contains("part_"));
        if (fileNames == null || fileNames.length == 0) {
            return "Nenhuma parte encontrada.";
        }

        String completeFile = dto.name() + "." + dto.mimetype();

        // Define o arquivo de saída unificado
        File finalFile = new File(definitivePath, completeFile);

        // Junta os arquivos dinamicamente
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(finalFile))) {
            byte[] buffer = new byte[8192]; // Buffer de 8KB
            
            for (File parte : fileNames) {
                try (FileInputStream in = new FileInputStream(parte)) {
                    int bytesLidos;
                    while ((bytesLidos = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesLidos);
                    }
                }
            }
            System.out.println("Arquivo unificado com sucesso em: " + finalFile.getAbsolutePath());
            
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao concatenar arquivos: " + e.getMessage());
        }

        return "Arquivo criado com sucesso";
    }

    public void testeFFMPEG(String miniaturePath, String userPath) throws IOException {
        File[] userFiles = new File(userPath).listFiles();

        for(File file:userFiles) {
            String fileName = file.getName().split("\\.")[0];

            FFmpeg ffmpeg = new FFmpeg(ffmpegPath + "ffmpeg.exe");
            FFprobe ffprobe = new FFprobe(ffmpegPath + "ffprobe.exe");
            
            FFmpegProbeResult in = ffprobe.probe(file.getPath());
            
            FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(in)
                .done()
                .addOutput(String.format("%s/%s.webp", miniaturePath, fileName))
                // flags=fast_bilinear: Altera o algoritmo de redimensionamento do FFmpeg para o mais rápido disponível (o padrão bicubic é lento).
                .setVideoFilter("scale=720:-1:flags=fast_bilinear")
                .setVideoCodec("webp")
                .addExtraArgs("-quality", "75")
                // -compression_level 0: Diz ao codec WebP para não perder tempo procurando a compressão perfeita. O nível 0 foca puramente em velocidade.
                .addExtraArgs("-compression_level", "0")  
                .done();
    
            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            executor.createJob(builder).run();
        }
        // FFmpegFormat format = in.getFormat();
        // System.out.println(format.);
    }

    public void extractThumbVideo() throws IOException {
        FFmpeg ffmpeg = new FFmpeg("C:/ffmpeg-2026-10-08-git-ec420ba161-essentials_build/bin/ffmpeg.exe");
        FFprobe ffprobe = new FFprobe("C:/ffmpeg-2026-10-08-git-ec420ba161-essentials_build/bin/ffprobe.exe");
        FFmpegBuilder builder = new FFmpegBuilder()
        .setInput("video.mp4")
        .done()
        .addExtraArgs("-ss", "00:00:01") // Pula direto para o segundo 1 (evita tela preta do início)
        .addOutput("frame_original.jpg")
        .addExtraArgs("-vframes", "1") // Extrai estritamente 1 frame
        .done();
    }
}
