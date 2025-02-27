package ru.mipt.currency.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.mipt.currency.server.models.ExchangeRate;

import java.util.Optional;

@Component
public class RestExchangeRateProviderAsync implements ExchangeRateProviderAsync {

    private final WebClient webClient;
    private final String serverUrl;

    public RestExchangeRateProviderAsync(WebClient webClient, String serverUrl) {
        this.webClient = webClient;
        this.serverUrl = serverUrl;
    }

    @Override
    public void processRate(String ccyPair, ExchangeRateReceiver receiver) {
        mockActivity();
        String url = String.format("%s/rates?ccyPair=%s", serverUrl, ccyPair);
        try {
            webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(ExchangeRate[].class)
                    .map(array -> array[0])
                    .subscribe(receiver::receive);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
