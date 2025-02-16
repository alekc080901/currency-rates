package ru.mipt.currency.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.mipt.currency.server.models.ExchangeRate;

import java.util.Optional;

@Component
public class RestCurrencyAdapter implements CurrencyAdapter {

    private final RestTemplate restTemplate;
    private final String serverUrl;

    public RestCurrencyAdapter(RestTemplate restTemplate, String serverUrl) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
    }

    @Override
    public Optional<ExchangeRate> getCurrency(String currencyCode) {
        String url = String.format("%s/rates/%s-%s", serverUrl,
                currencyCode.substring(0, 3), currencyCode.substring(3, 6));
        try {
            return Optional.ofNullable(restTemplate.getForObject(url, ExchangeRate.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
