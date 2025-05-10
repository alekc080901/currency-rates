package ru.mipt.currency.client;

import ru.mipt.currency.server.models.ExchangeRate;

public interface ExchangeRateReceiver {

    void receive(ExchangeRate rate);
}
