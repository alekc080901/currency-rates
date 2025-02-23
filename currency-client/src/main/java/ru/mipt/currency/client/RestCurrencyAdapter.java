package ru.mipt.currency.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.mipt.currency.server.models.ExchangeRate;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Component
public class RestCurrencyAdapter implements CurrencyAdapter {

    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final String serverUrl;

    public RestCurrencyAdapter(RestTemplate restTemplate, WebClient webClient, String serverUrl) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
        this.serverUrl = serverUrl;
    }

    @Override
    public ExchangeRate getCurrencySync(String currencyCode) {
        mockActivity();
        try {
            String url = String.format("%s/rates?ccyPair=%s", serverUrl, currencyCode);
            return Objects.requireNonNull(restTemplate.getForObject(url, ExchangeRate[].class))[0];
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        } finally {
            mockActivity();
        }
    }

    @Override
    public Mono<ExchangeRate> getCurrencyAsync(String url) {
        mockActivity();
        try {
            return webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(ExchangeRate[].class)
                    .map(array -> array[0]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
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
