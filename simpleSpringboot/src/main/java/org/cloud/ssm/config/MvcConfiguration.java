package org.cloud.ssm.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan(useDefaultFilters = false, basePackages = { "org.cloud.ssm" })
public class MvcConfiguration implements WebMvcConfigurer {

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new PostAndPutCommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(500000000);
        return multipartResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    class PostAndPutCommonsMultipartResolver extends CommonsMultipartResolver {
        @Override
        public boolean isMultipart(HttpServletRequest request) {
            String method = request.getMethod().toLowerCase();
            // By default, only POST is allowed. Since this is an 'update' we should accept
            // PUT.
            if (!Arrays.asList("put", "post").contains(method)) {
                return false;
            }
            String contentType = request.getContentType();
            return (contentType != null && contentType.toLowerCase().startsWith("multipart/"));
        }
    }
}
