package ru.mipt.currency.client;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CurrencyProfiler {

    private final CurrencyAdapter currencyAdapter;

    public CurrencyProfiler(CurrencyAdapter currencyAdapter) {
        this.currencyAdapter = currencyAdapter;
    }

    @Scheduled(fixedRate = 5000)
    private void getUsdRubRate() {
        currencyAdapter.getCurrency("USDRUB")
                .doOnError(error -> System.out.println("Can't get response from the server: " +
                        error.getMessage()))
                .blockOptional()
                .ifPresent(System.out::println);
    }
}
