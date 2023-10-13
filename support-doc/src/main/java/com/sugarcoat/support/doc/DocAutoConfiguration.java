package com.sugarcoat.support.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiBuilderCustomizer;
import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.providers.JavadocProvider;
import org.springdoc.core.service.OpenAPIService;
import org.springdoc.core.service.SecurityService;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Swagger 自动配置类
 */
@ConditionalOnClass({OpenAPI.class})
@EnableConfigurationProperties(DocProperties.class)
@ConditionalOnProperty(prefix = "sugarcoat.doc", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DocAutoConfiguration {

    // ========== 全局 OpenAPI 配置 ==========

    @Bean
    public OpenAPI createApi(DocProperties properties) {
        Map<String, SecurityScheme> securitySchemas = buildSecuritySchemes();
        OpenAPI openAPI = new OpenAPI()
                // 接口信息
                .info(buildInfo(properties))
                // 接口安全配置
                .components(new Components()
                        .securitySchemes(securitySchemas)
                );
        securitySchemas.keySet().forEach(key -> openAPI.addSecurityItem(new SecurityRequirement().addList(key)));
        return openAPI;
    }

    /**
     * API 摘要信息
     */
    private Info buildInfo(DocProperties properties) {
        return new Info()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .contact(new Contact()
                        .name(properties.getAuthor())
                        .url(properties.getUrl())
                        .email(properties.getEmail()))
                .license(new License()
                        .name(properties.getLicense())
                        .url(properties.getLicenseUrl()));
    }

    /**
     * 安全模式，这里配置通过请求头 Authorization 传递 token 参数
     */
    private Map<String, SecurityScheme> buildSecuritySchemes() {
        Map<String, SecurityScheme> securitySchemes = new HashMap<>();
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY) // 类型
                .name(HttpHeaders.AUTHORIZATION) // 请求头的 name
                // token 所在位置
                .in(SecurityScheme.In.HEADER);
        securitySchemes.put(HttpHeaders.AUTHORIZATION, securityScheme);
        return securitySchemes;
    }

    /**
     * 自定义 OpenAPI 处理器
     */
    @Bean
    public OpenAPIService openApiBuilder(Optional<OpenAPI> openAPI, SecurityService securityParser,
                                         SpringDocConfigProperties springDocConfigProperties, PropertyResolverUtils propertyResolverUtils,
                                         Optional<List<OpenApiBuilderCustomizer>> openApiBuilderCustomizers,
                                         Optional<List<ServerBaseUrlCustomizer>> serverBaseUrlCustomizers,
                                         Optional<JavadocProvider> javadocProvider) {

        return new OpenAPIService(openAPI, securityParser, springDocConfigProperties, propertyResolverUtils,
                openApiBuilderCustomizers, serverBaseUrlCustomizers, javadocProvider);
    }

    // ========== 分组 OpenAPI 配置 ==========

    /**
     * 所有模块的 API 分组
     */
    @Bean
    public GroupedOpenApi allGroupedOpenApi() {
        return buildGroupedOpenApi("全部", "/**");
    }

    public static GroupedOpenApi buildGroupedOpenApi(String groupName, String matchPath) {
        return GroupedOpenApi.builder()
                .group(groupName)
                .pathsToMatch(matchPath)
                .addOperationCustomizer(
                        (operation, handlerMethod) -> operation
                                .addParametersItem(buildTenantHeaderParameter())
                                .addParametersItem(buildSecurityHeaderParameter())
                )
                .build();
    }

    /**
     * 构建 Tenant 租户编号请求头参数
     */
    private static Parameter buildTenantHeaderParameter() {
        return new Parameter()
                .name("TENANT_ID") // header名
                .description("租户编号") // 描述
                .in(String.valueOf(SecurityScheme.In.HEADER)) // 请求 header
                .schema(new IntegerSchema()
                        ._default(1L)
                        .name("TENANT_ID")
                        .description("租户编号")
                );
    }

    /**
     * 构建 Authorization 认证请求头参数
     */
    private static Parameter buildSecurityHeaderParameter() {
        return new Parameter()
                .name(HttpHeaders.AUTHORIZATION) // header 名
                .description("Token") // 描述
                .in(String.valueOf(SecurityScheme.In.HEADER)) // 请求 header
                .schema(new StringSchema()
                        ._default("Bearer test1")
                        .name("HEADER_TENANT_ID")
                        .description("Token")
                );
    }

}
