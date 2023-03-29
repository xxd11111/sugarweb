package com.xxd.sugarcoat.support.undo.doc;

//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.ExternalDocumentation;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
//import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Configuration;

/**
 * @author xxd
 * @description TODO
 * @date 2022-11-24
 */
@Configuration
public class OpenApiConfig {

    //@Bean
    //public OpenAPI springShopOpenApi() {
    //    final String loginToken = "BearerAuth";
    //    return new OpenAPI().info(new Info().title("Simple Boot API")
    //                    .description("SpringBoot基础框架")
    //                    .version("v1.0.0")).externalDocs(new ExternalDocumentation()
    //                    .description("SpringBoot基础框架")
    //                    .url("http://127.0.0.1:8889"))
    //            .components(new Components().addSecuritySchemes(loginToken, new SecurityScheme()
    //                    .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
    //                    .in(SecurityScheme.In.HEADER)
    //                    .name(loginToken)))
    //            .addSecurityItem(new SecurityRequirement().addList(loginToken));
    //}
    //
    //@Bean
    //public GroupedOpenApi systemApi() {
    //    return GroupedOpenApi.builder().group("demo1系统模块")
    //            .pathsToMatch("/demo1/**")
    //            .build();
    //}
    //
    //@Bean
    //public GroupedOpenApi authApi() {
    //    return GroupedOpenApi.builder().group("demo2权限模块")
    //            .pathsToMatch("/demo2/**")
    //            .build();
    //}

}
