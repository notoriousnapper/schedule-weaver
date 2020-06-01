package com.jesse.personal.scheduleweaver.output;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfigurations {

    @Bean
    FileConfigurer fileConfigurer() {
//        return new FileConfigurer("src/main/resources/action"); // relative path
        return new FileConfigurer("/Users/renly/Library/Mobile Documents/com~apple~CloudDocs/Jesse's Documents/scheduleweaver"); // relative path
    }
}
