package ru.mipt.currency.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public String serverUrl(@Value("${app.currency.address}") String address, @Value("${app.currency.port}") String port) {
        return String.format("http://%s:%s", address, port);
    }
}
