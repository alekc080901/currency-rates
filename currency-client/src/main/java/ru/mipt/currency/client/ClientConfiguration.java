package ru.mipt.currency.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;


@Configuration
@EnableFeignClients(basePackageClasses = {ClientApplication.class})
public class ClientConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebClient webClient() {
        LoopResources loopResources = LoopResources.create("loop", 2, true);
        HttpClient httpClient = HttpClient.create()
                .runOn(loopResources);
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @Bean
    public String serverUrl(@Value("${app.currency.address}") String address, @Value("${app.currency.port}") String port) {
        return String.format("http://%s:%s", address, port);
    }
}
