package ru.mipt.currency.client;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mipt.currency.client.sync.ExchangeRateProvider;

@Component
public class CurrencyScheduler {

    private final ExchangeRateProvider exchangeRateProvider;

    public CurrencyScheduler(ExchangeRateProvider exchangeRateProvider) {
        this.exchangeRateProvider = exchangeRateProvider;
    }

    @Scheduled(fixedRate = 5000)
    private void getUsdRubRate() {
        exchangeRateProvider.getRate("USDRUB")
                .ifPresentOrElse(System.out::println, () -> System.out.println("Can't get response from the server"));
    }
}
