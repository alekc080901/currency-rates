package ru.mipt.currency.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.mipt.currency.server.models.ExchangeRate;

import java.util.Optional;

@Component
public class CurrencyProfiler {

    private final CurrencyAdapter currencyAdapter;

    public CurrencyProfiler(CurrencyAdapter currencyAdapter) {
        this.currencyAdapter = currencyAdapter;
    }

//    @Scheduled(fixedRate = 5000)
//    private void getUsdRubRate(@Value("${app.run-async}") boolean runAsync) {
//        Optional<ExchangeRate> usdrub = runAsync ? currencyAdapter.getCurrencyAsync("USDRUB").join() :
//                currencyAdapter.getCurrencySync("USDRUB");
//        usdrub.ifPresentOrElse(
//                System.out::println,
//                () -> System.out.println("Can't get respsonse from the server")
//        );
//    }
}
