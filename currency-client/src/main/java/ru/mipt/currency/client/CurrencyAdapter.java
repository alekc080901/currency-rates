package ru.mipt.currency.client;

import reactor.core.publisher.Mono;
import ru.mipt.currency.server.models.ExchangeRate;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface CurrencyAdapter {

    Mono<ExchangeRate> getCurrency(String currencyCode);
}
