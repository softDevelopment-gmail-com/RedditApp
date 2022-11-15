package uz.company.redditapp.config.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import uz.company.redditapp.errors.ErrorDto;
import uz.company.redditapp.errors.IntegrationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
@Slf4j
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == CLIENT_ERROR || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        String responseAsString = toString(httpResponse.getBody());
        ErrorDto errorDTO = null;
        try {
            errorDTO = new ObjectMapper().readValue(responseAsString, ErrorDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        throw new IntegrationException(responseAsString, errorDTO);
    }

    String toString(InputStream inputStream) {
        try {
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                inputStream.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }

}
