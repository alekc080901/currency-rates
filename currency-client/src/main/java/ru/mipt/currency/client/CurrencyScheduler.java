package ru.mipt.currency.client;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CurrencyScheduler {

    private final CurrencyAdapter currencyAdapter;

    public CurrencyScheduler(CurrencyAdapter currencyAdapter) {
        this.currencyAdapter = currencyAdapter;
    }

    @Scheduled(fixedRate = 5000)
    private void getUsdRubRate() {
        currencyAdapter.getCurrency("USDRUB")
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Can't get respsonse from the server")
                );
        }
}
