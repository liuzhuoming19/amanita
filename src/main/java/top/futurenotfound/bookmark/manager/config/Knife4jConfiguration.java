package top.futurenotfound.bookmark.manager.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.futurenotfound.bookmark.manager.env.Constant;

import java.util.List;

/**
 * knife4j配置
 *
 * @author liuzhuoming
 */
@Configuration
@EnableSwagger2
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        //.title("swagger-bootstrap-ui-demo RESTful APIs")
                        .description("# Bookmark Manager RESTful APIs")
                        .termsOfServiceUrl("https://futurenotfound.top")
                        .contact(
                                new Contact(
                                        "liuzhuoming",
                                        "https://futurenotfound.top",
                                        "liuzhuoming23@live.com"
                                )
                        )
                        .version("0.0.1")
                        .build())
                //分组名称
                .groupName("0.x.x版本")
                .globalRequestParameters(parameters())
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)
                        .and(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<SecurityScheme> securitySchemes() {
        return Lists.newArrayList(
                new ApiKey(Constant.HEADER_AUTHORIZATION, Constant.HEADER_AUTHORIZATION, "header"));
    }

    private List<SecurityContext> securityContexts() {
        return Lists.newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .operationSelector(
                                operationContext -> !operationContext.requestMappingPattern().matches("/login/*")
                        )
                        .build()
        );
    }

    private List<SecurityReference> defaultAuth() {
        return Lists.newArrayList(
                new SecurityReference(Constant.HEADER_AUTHORIZATION, ArrayUtils.toArray(new AuthorizationScope("global", "accessEverything"))));
    }

    private List<RequestParameter> parameters() {
        return List.of(
                new RequestParameterBuilder()
                        .name(Constant.HEADER_SOURCE)
                        .description("请求来源 WEB网站请求 API外部api请求")
                        .in(ParameterType.HEADER)
                        .required(true)
                        .build()
        );
    }
}
