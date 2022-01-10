package com.jmsc.app.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2).select()                                  
        											  .apis(RequestHandlerSelectors.any())              
        											  .paths(PathSelectors.any())                          
        											  .build();                                           
    }
    
//    /**
//     * Accessing static resources
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        /**
//         * SpringBoot autoconfiguration itself does not map the /swagger-ui.html
//         * This path is mapped to the corresponding directory META-INF/resources/
//         * Use WebMvcConfigurerAdapter to publish the static files of swagger;
//         */
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        //Map all /static/** accesses to the classpath:/static/ directory
//        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX +"/static/");
//        super.addResourceHandlers(registry);
//    }
}
