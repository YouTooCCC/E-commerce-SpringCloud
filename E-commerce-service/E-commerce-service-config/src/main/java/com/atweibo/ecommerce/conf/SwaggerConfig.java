package com.atweibo.ecommerce.conf;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @Description Swagger配置类
 * 原生：/swagger-ui.html
 * 美化：/doc.html
 *
 * @Author weibo
 * @Data 2022/11/19 11:08
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*Swagger 实例 Bean 是Docket，所以通过配置Docket实例来配置Swagger*/
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.atweibo.ecommerce"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @return springfox.documentation.service.ApiInfo
     * @Describe:       配置Swagger的描述信息
     * @author Weibo
     * @date 2022/11/19 12:11
     */
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("imooc-mirco-service")
                .description("e-cimmerce-springcloud-servic")
                .contact(new Contact("weibo", "www.imooc.com", "82247559@qq.com"))
                .version("1.0")
                .build();
    }

}
