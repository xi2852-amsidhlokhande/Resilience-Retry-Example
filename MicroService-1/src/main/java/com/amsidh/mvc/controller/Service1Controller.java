package com.amsidh.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RequiredArgsConstructor
@RestController
@RequestMapping("/service1")
@Slf4j
public class Service1Controller {

    private final static String SERVICE2_URL = "http://localhost:8082/service2/sayHello4";
    private final RestTemplate restTemplate;

    @GetMapping("/sayHello1")
    public String sayHello() throws Exception {
        log.info("Calling Service2");
        try {

            /*HttpHeaders headers = new HttpHeaders();
            headers.add("x-b3-traceid", "0000000000000001");
            headers.add("x-b3-spanid", "0000000000000002");
            HttpEntity entity = new HttpEntity(headers);

            ResponseEntity<String> responseEntityService2 = restTemplate.exchange(SERVICE2_URL, HttpMethod.GET, entity, String.class);*/
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(SERVICE2_URL, String.class);
            log.info("Response from Service2 is {}", responseEntity.getBody(), kv("status", responseEntity.getStatusCodeValue()));
        } catch (HttpClientErrorException httpClientErrorException) {
            log.error(httpClientErrorException.getMessage());
            throw httpClientErrorException;
        } catch (RestClientResponseException restClientResponseException) {
            log.error(restClientResponseException.getMessage());
            throw restClientResponseException;
        } catch (RestClientException restClientException) {
            log.error(restClientException.getMessage());
            throw restClientException;
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        return "Service-1 Message-1";
    }


}