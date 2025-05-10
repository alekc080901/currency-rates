package ru.mipt.currency.client;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CurrencyProfiler {

    private final ExchangeRateProvider exchangeRateProvider;

    public CurrencyProfiler(ExchangeRateProvider exchangeRateProvider) {
        this.exchangeRateProvider = exchangeRateProvider;
    }

//    @Scheduled(fixedRate = 5000)
//    private void getUsdRubRate() {
//        exchangeRateProvider.getCurrency("USDRUB")
//                .doOnError(error -> System.out.println("Can't get response from the server: " +
//                        error.getMessage()))
//                .blockOptional()
//                .ifPresent(System.out::println);
//    }
}
