package com.example.contentcashingrequest.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.contentcashingrequest.common.session.service.InMemoryClientSessionService;

@Configuration
public class AppConfiguration {

    @Bean
    public InMemoryClientSessionService clientSessionService() {
        return new InMemoryClientSessionService();
    }


}
