package com.dsm.pick.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ScheduleConfiguration {

    @Bean
    public ThreadPoolTaskExecutor createDefaultTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(15);
        threadPoolTaskExecutor.setCorePoolSize(15);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
