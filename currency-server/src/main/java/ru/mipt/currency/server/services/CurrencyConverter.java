package ru.mipt.currency.server.services;

import org.springframework.stereotype.Service;
import ru.mipt.currency.server.models.ExchangeRate;
import ru.mipt.currency.server.utils.MathUtils;
import ru.mipt.currency.server.utils.RandomUtils;

import java.util.HashMap;
import java.util.Map;
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

    public double calculate(String ccyPair) {
        double rate = RandomUtils.deviate(exchangeRates.get(ccyPair), RANDOM_DEVIATION);
        return MathUtils.round(rate, CURRENCY_PRECISION);
    }
}
