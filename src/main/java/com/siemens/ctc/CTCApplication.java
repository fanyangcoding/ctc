package com.siemens.ctc;

import com.github.pagehelper.PageHelper;
import com.siemens.ctc.config.MyPropsConstants;
import org.apache.catalina.filters.CorsFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Properties;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MultipartAutoConfiguration.class})
@MapperScan("com.siemens.ctc.dao")
@EnableConfigurationProperties({MyPropsConstants.class})
@ServletComponentScan
//@EnableSwagger2
@EnableWebMvc
public class CTCApplication {
    private static final Logger LOGGER = LogManager.getLogger(CTCApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CTCApplication.class, args);
    }

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();
        props.setProperty("offsetAsPageNum", "true");
        props.setProperty("rowBoundsWithCount", "true");
        props.setProperty("reasonable", "true");
        props.setProperty("dialect", "postgresql");
        pageHelper.setProperties(props);
        return pageHelper;
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "|{}[]\\"));
        return factory;
    }

    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        LOGGER.info("######################## Loading the multipart resolver");
        CustomMultipartResolver multipartResolver = new CustomMultipartResolver();
        multipartResolver.setMaxUploadSize(1024 * 1024 * 2000);
        multipartResolver.setDefaultEncoding("UTF-8");
        multipartResolver.setResolveLazily(true);
        return multipartResolver;
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig());
//        return new CorsFilter(source);
//
//    }
//
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setMaxAge(3600L);
//        return corsConfiguration;
//    }

}
