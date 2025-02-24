package ru.mipt.currency.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.mipt.currency.server.models.ExchangeRate;

@Component
public class RestCurrencyAdapter implements CurrencyAdapter {

    private final WebClient webClient;
    private final String serverUrl;

    public RestCurrencyAdapter(WebClient webClient, String serverUrl) {
        this.webClient = webClient;
        this.serverUrl = serverUrl;
    }

    @Override
    public Mono<ExchangeRate> getCurrency(String currencyCode) {
        mockActivity();
        String url = String.format("%s/rates?ccyPair=%s", serverUrl, currencyCode);
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
