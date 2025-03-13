package com.angus.pethomeadoptionbackend.configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.PathContainer;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpHeaders.*;

@Configuration
public class CorsConfig {

    private static final String X_REQUESTED_WITH = "X-Requested-With" ;

    @Bean
    public CorsFilter corsFilter() {

        var urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        //if request come from 127.0.0.1, it will be allowed
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3001"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setExposedHeaders(List.of(ORIGIN, ACCESS_CONTROL_ALLOW_ORIGIN , CONTENT_TYPE , ACCEPT,
                AUTHORIZATION, X_REQUESTED_WITH, ACCESS_CONTROL_ALLOW_METHODS , ACCESS_CONTROL_ALLOW_HEADERS  ));
        corsConfiguration.addAllowedHeader("*");


        corsConfiguration.setMaxAge(3600L);
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);

    }

}
