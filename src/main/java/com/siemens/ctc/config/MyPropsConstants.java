package com.siemens.ctc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "custom")
@Data
public class MyPropsConstants {

    private String uploadBasePath;

    private String thumbnailBasePath;

    private int maxPinNum;

}
