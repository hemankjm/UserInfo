package petfriends.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;



import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;


import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // private static final String API_NAME = "Study API";
    // private static final String API_VERSION = "0.0.1";
    // private static final String API_DESCRIPTION = "Study API 명세서";

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiInfo apiInfo() {
      return new ApiInfo("My REST API",
          "Some custom description of API.",
          "1.0",
          "Terms of service",
          new Contact("Sallo Szrajbman", "www.baeldung.com", "salloszraj@gmail.com"),
          "License of API",
          "API license URL",
          Collections.emptyList());
    }

    @Bean
    public Docket api() {
      return new Docket(DocumentationType.SWAGGER_2)
          .apiInfo(apiInfo())
          .securityContexts(Arrays.asList(securityContext()))
          .securitySchemes(Arrays.asList(apiKey()))
          .select()
          .apis(RequestHandlerSelectors.any())
          .paths(PathSelectors.any())
          .build();
    }

    private ApiKey apiKey() {
      return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
      return SecurityContext.builder()
          .securityReferences(defaultAuth())
          .build();
    }

    List<SecurityReference> defaultAuth() {
      AuthorizationScope authorizationScope
          = new AuthorizationScope("global", "accessEverything");
      AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
      authorizationScopes[0] = authorizationScope;
      return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }


// ---------------------------------------------------------------
    // @Bean
    // public Docket api() {
    //     return new Docket(DocumentationType.SWAGGER_2)
    //             .useDefaultResponseMessages(false)
    //             .select()
    //             .apis(RequestHandlerSelectors.basePackage("org.greenbyme.angelhack"))
    //             .paths(PathSelectors.ant("/api/**"))
    //             .build()
    //             .apiInfo(metaData())
    //             .securityContexts(Arrays.asList(securityContext()))
    //             .securitySchemes(Arrays.asList(apiKey()));

    // }

    // private ApiInfo metaData() {
    //     return new ApiInfoBuilder()
    //             .title("GreenByMe REST API")
    //             .description("Green by me, Green by earth(us)")
    //             .version("0.4.0")
    //             .termsOfServiceUrl("Terms of service")
    //             .contact(new Contact("Tae Jeong, Da hun", "https://github.com/GreenByMe/GreenByMe_Server", "xowjd41@naver.com"))
    //             .license("Apache License Version 2.0")
    //             .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
    //             .build();
    // }

    // private ApiKey apiKey() {
    //     return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    // }

    // private SecurityContext securityContext() {
    //     return springfox
    //             .documentation
    //             .spi.service
    //             .contexts
    //             .SecurityContext
    //             .builder()
    //             .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    // }

    // List<SecurityReference> defaultAuth() {
    //     AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    //     AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    //     authorizationScopes[0] = authorizationScope;
    //     return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    // }
// ------------------------------------------------


    // @Bean
    // public Docket api() {
    //     Parameter parameterBuilder = new ParameterBuilder()
    //         .name(HttpHeaders.AUTHORIZATION)
    //         .description("Access Tocken")
    //         .modelRef(new ModelRef("string"))
    //         .parameterType("header")
    //         .required(false)
    //         .build();

    //     List<Parameter> globalParamters = new ArrayList<>();
    //     globalParamters.add(parameterBuilder);

    //     return new Docket(DocumentationType.SWAGGER_2)
    //             .globalOperationParameters(globalParamters)
    //             .apiInfo(apiInfo())
    //             .select()
    //             .apis(RequestHandlerSelectors.basePackage("petfriends.userInfo"))
    //             .paths(PathSelectors.any())
    //             .build();
    // }

    // public ApiInfo apiInfo() {
	// return new ApiInfoBuilder()
	// 	.title(API_NAME)
	// 	.version(API_VERSION)
	// 	.description(API_DESCRIPTION)
	// 	.build();
    // }
}