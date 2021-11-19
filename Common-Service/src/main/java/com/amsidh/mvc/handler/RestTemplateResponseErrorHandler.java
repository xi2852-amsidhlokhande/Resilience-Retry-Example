package com.amsidh.mvc.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ExtractingResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Component
@Slf4j
public class RestTemplateResponseErrorHandler extends ExtractingResponseErrorHandler {

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        log.error(response.getStatusCode().getReasonPhrase(), kv("status", response.getRawStatusCode()), kv("response", response));
        super.handleError(url, method, response);
    }
}
