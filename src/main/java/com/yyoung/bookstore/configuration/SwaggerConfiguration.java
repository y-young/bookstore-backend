package com.yyoung.bookstore.configuration;

import com.fasterxml.classmate.TypeResolver;
import com.yyoung.bookstore.constants.SecurityConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Data
class SwaggerPageable {
    @ApiModelProperty(value = "Number of records per page", example = "10")
    private Integer size;

    @ApiModelProperty(value = "Page number", example = "0")
    private Integer page;

    @ApiModelProperty(
            value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported."
    )
    private String sort;
}

@Configuration
public class SwaggerConfiguration {
    private final TypeResolver typeResolver;

    public SwaggerConfiguration(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yyoung.bookstore"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(Pageable.class, SwaggerPageable.class)
                .consumes(new HashSet<>(Collections.singleton(MediaType.APPLICATION_JSON_VALUE)))
                .produces(new HashSet<>(Collections.singleton(MediaType.APPLICATION_JSON_VALUE)))
                .securityContexts(securityContext())
                .securitySchemes(securitySchemes());
    }

    // Display authorization options in Swagger UI
    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(new ApiKey("JWT", SecurityConstants.TOKEN_HEADER, "header"));
    }

    // Add authorization context when trying API in Swagger UI
    private List<SecurityContext> securityContext() {
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
        return Collections.singletonList(securityContext);
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Bookstore Backend")
                .build();
    }
}
