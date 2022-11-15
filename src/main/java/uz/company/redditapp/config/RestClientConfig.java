package uz.company.redditapp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import uz.company.redditapp.config.interceptor.RestTemplateResponseErrorHandler;

/**
 * Powered by: Shuxratjon Rayimjonov
 * Date: 10.10.2022 18:08
 */
@Configuration
@Slf4j
public class RestClientConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(30000);
        factory.setReadTimeout(30000);
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getInterceptors().add(new RestTemplateLoggingInterceptor());
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        return restTemplate;
    }
}
