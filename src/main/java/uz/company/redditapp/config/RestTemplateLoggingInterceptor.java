package uz.company.redditapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022  18:12
 */
@Component
@Slf4j
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution)
        throws IOException {
        logRequest(httpRequest, bytes);
        ClientHttpResponse response = clientHttpRequestExecution.execute(httpRequest, bytes);
        logResponse(response);

        return response;
    }


    private void logRequest(HttpRequest request, byte[] body) {
        log.info("Date        : {}", LocalDateTime.now());
        log.info("URI         : {}", request.getURI());
        log.info("Method      : {}", request.getMethod());
        log.info("Headers     : {}", request.getHeaders());
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        log.info("Date         : {}", LocalDateTime.now());
        log.info("Status code  : {}", response.getStatusCode());
        log.info("Headers      : {}", response.getHeaders());
    }
}
