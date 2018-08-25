package com.twsz.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.profiles")
@Data
@NoArgsConstructor
public class ProfileConfig {
    private String active;
}
