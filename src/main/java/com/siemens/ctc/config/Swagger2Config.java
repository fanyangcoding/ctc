package com.siemens.ctc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
//        ParameterBuilder tokenParam = new ParameterBuilder();
//        List<Parameter> parameters = new ArrayList<>();
//        tokenParam.name("Authorization").description("JWT token").modelRef(new ModelRef("String")).parameterType("header").required(false).build();
//        parameters.add(tokenParam.build());
        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo()).forCodeGeneration(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.siemens.ctc.controller"))
                .paths(PathSelectors.any())
                .build()
//                .globalOperationParameters(parameters)
                .apiInfo(apiInfo())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(new ApiKey("Authorization", "Bearer", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("CTC REST API Interface 设计文档")
                .description("CTC REST API Interface")
                .version("0.8.0")
                .build();
    }

//    @Bean
////    public Docket userApi() {
////        List<ResponseMessage> responseMessageList = new ArrayList<>();
////        responseMessageList.add(new ResponseMessageBuilder().code(401).message("未授权").responseModel(new ModelRef("ApiError")).build());
////        responseMessageList.add(new ResponseMessageBuilder().code(403).message("找不到资源").responseModel(new ModelRef("ApiError")).build());
////        responseMessageList.add(new ResponseMessageBuilder().code(404).message("无权限").responseModel(new ModelRef("ApiError")).build());
////        responseMessageList.add(new ResponseMessageBuilder().code(409).message("业务逻辑异常").responseModel(new ModelRef("ApiError")).build());
////        responseMessageList.add(new ResponseMessageBuilder().code(422).message("参数检验异常").responseModel(new ModelRef("ApiError")).build());
////        responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部异常").responseModel(new ModelRef("ApiError")).build());
////
////        return new Docket(DocumentationType.SWAGGER_2)
////                .globalResponseMessage(RequestMethod.GET, responseMessageList)
////                .globalResponseMessage(RequestMethod.POST, responseMessageList)
////                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
////                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
////                .apiInfo(apiInfo());
////    }
}
