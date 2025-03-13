package com.angus.pethomeadoptionbackend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

    @Value("${IMG_FOLDER_URL}")
    private String imageBaseUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //String formattedPath = imageBaseUrl.endsWith("/") ? imageBaseUrl : imageBaseUrl + "/";

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imageBaseUrl);
    }
}
