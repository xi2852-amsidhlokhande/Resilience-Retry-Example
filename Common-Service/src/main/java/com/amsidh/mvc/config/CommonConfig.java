package com.amsidh.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CommonConfig {

    //private final RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
        return restTemplate;
    }

}
