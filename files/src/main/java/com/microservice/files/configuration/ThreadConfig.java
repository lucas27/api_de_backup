package com.microservice.files.configuration;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync 
public class ThreadConfig {
    @Bean 
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Define quantas threads começam ativas (padrão são 8)
        executor.setMaxPoolSize(50); // Define limite máximo de threads
        executor.setQueueCapacity(100); // Define a capacidade de fila para não estourar a memória do servidor
        executor.setThreadNamePrefix("thread-"); // Define o nome de cada thread
        executor.initialize();
        return executor;
    }
}
