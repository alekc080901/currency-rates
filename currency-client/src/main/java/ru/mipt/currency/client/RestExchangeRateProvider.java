package ru.mipt.currency.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import ru.mipt.currency.server.models.ExchangeRate;

import java.util.Optional;

@Component
public class RestExchangeRateProvider implements ExchangeRateProvider {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    public RestExchangeRateProvider(RestTemplate restTemplate, String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    @Override
    public Optional<ExchangeRate> getRate(String ccyPair) {
        mockActivity();
        String url = String.format("%s/rates?ccyPair=%s", serverUrl, ccyPair);
        try {
            return Optional.ofNullable(restTemplate.getForObject(url, ExchangeRate[].class))
                    .map(array -> array[0]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        } finally {
            mockActivity();
        }
    }

    private static void mockActivity() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException("Sleep interrupted", e);
        }
    }
}
