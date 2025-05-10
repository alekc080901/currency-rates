package ru.mipt.currency.client.async;

import ru.mipt.common.dto.ExchangeRate;

public interface ExchangeRateReceiver {

    void receive(ExchangeRate rate);
}
