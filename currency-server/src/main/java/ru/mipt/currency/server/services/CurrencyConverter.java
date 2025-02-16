package ru.mipt.currency.server.services;

import org.springframework.stereotype.Service;
import ru.mipt.currency.server.configuration.CurrencyConfiguration;
import ru.mipt.currency.server.models.ExchangeRate;
import ru.mipt.currency.server.utils.MathUtils;
import ru.mipt.currency.server.utils.RandomUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyConverter {

    private static final double RANDOM_DEVIATION = 0.1;
    private static final int CURRENCY_PRECISION = 3;

    private final Map<String, Map<String, Double>> exchangeRates;

    public CurrencyConverter(CurrencyConfiguration currencyConfiguration) {
        this.exchangeRates = constructExchangeRate(currencyConfiguration);
    }

    private Map<String, Map<String, Double>> constructExchangeRate(CurrencyConfiguration currencyConfiguration) {
        Map<String, Map<String, Double>> exchangeRates = new HashMap<>();
        for (ExchangeRate exchangeRate : currencyConfiguration.getExchangeRate()) {
            exchangeRates.computeIfAbsent(exchangeRate.getFrom(), k -> new HashMap<>());
            exchangeRates.get(exchangeRate.getFrom()).put(exchangeRate.getTo(), exchangeRate.getValue());
        }
        return exchangeRates;
    }

    public double calculate(String from, String to) {
        double rate = RandomUtils.deviate(exchangeRates.get(from).get(to), RANDOM_DEVIATION);
        return MathUtils.round(rate, CURRENCY_PRECISION);
    }
}
