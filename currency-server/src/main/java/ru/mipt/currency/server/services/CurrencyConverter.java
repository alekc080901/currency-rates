package ru.mipt.currency.server.services;

import org.springframework.stereotype.Service;
import ru.mipt.currency.server.Utils;
import ru.mipt.currency.server.models.ExchangeRate;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CurrencyConverter {

    private static final double RANDOM_DEVIATION = 0.1;
    private static final int CURRENCY_PRECISION = 3;

    private final Map<String, Double> exchangeRates;

    public CurrencyConverter(CurrencyConfiguration currencyConfiguration) {
        this.exchangeRates = constructExchangeRate(currencyConfiguration);
    }

    private Map<String, Double> constructExchangeRate(CurrencyConfiguration currencyConfiguration) {
        return currencyConfiguration.getExchangeRate()
                .stream()
                .collect(Collectors.toMap(ExchangeRate::getCcyPair, ExchangeRate::getValue));
    }

    public Double calculate(String ccyPair) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException("Waiting was interrupted", e);
        }
        return Optional.ofNullable(exchangeRates.get(ccyPair))
                .map(rate -> Utils.deviate(rate, RANDOM_DEVIATION))
                .orElse(-1.0);
    }
}
