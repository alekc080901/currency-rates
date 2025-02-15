package ru.mipt.currency.client;

import ru.mipt.currency.server.models.ExchangeRate;

import java.util.Optional;

public interface CurrencyAdapter {

    Optional<ExchangeRate> getCurrency(String currencyCode);
}
