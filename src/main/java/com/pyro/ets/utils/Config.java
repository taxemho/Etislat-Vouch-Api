package com.pyro.ets.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class Config {

    @Value("${log.dir.name}")
    private String logDirName;

    @Bean
    public String logDirName() {
        return logDirName;
    }
}
