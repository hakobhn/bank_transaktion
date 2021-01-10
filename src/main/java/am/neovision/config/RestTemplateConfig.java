package am.neovision.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author hakob.hakobyan created on 12/28/2020
 */
@Configuration
public class RestTemplateConfig {

    @Autowired
    LoggingInterceptor loggingInterceptor;
    @Value("${rest.timeout}")
    private Integer timeout;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        RestTemplate restTemplate = builder
                .setConnectTimeout(Duration.ofMillis(timeout))
                .setReadTimeout(Duration.ofMillis(timeout))
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();

        return restTemplate;
    }

}
