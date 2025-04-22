package com.gpoint.files_service.config.async;

import lombok.RequiredArgsConstructor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@RequiredArgsConstructor
public class AsyncConfig implements AsyncConfigurer {

    private final AsyncProperty asyncProperty;

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }

    @Bean
    public Executor s3ExecutorService() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncProperty.getSettings().get("s3").getCorePoolSize());
        executor.setMaxPoolSize(asyncProperty.getSettings().get("s3").getMaxPoolSize());
        executor.setQueueCapacity(asyncProperty.getSettings().get("s3").getQueueCapacity());
        executor.setThreadNamePrefix("S3AsyncThread-");
        executor.initialize();
        return executor;
    }
}
