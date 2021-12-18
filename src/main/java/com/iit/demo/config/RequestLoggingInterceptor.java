/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iit.demo.config;

/**
 *
 * @author Farouk
 */
import org.slf4j.*;
import org.springframework.http.*;
import java.io.IOException;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class RequestLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final static Logger log = LoggerFactory.getLogger(RequestLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response = execution.execute(request, body);

        log.debug("request method: {}, request URI: {},request Query: {}, request headers: {},  response status code: {}, response headers: {}",
                request.getMethod(),
                request.getURI(),
                request.getURI().getRawQuery(),
                request.getHeaders(),
                response.getStatusCode(),
                response.getHeaders()
        );

        return response;
    }
}
