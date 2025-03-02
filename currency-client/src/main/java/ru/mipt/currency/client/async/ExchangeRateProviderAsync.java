package ru.mipt.currency.client.async;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

public interface ExchangeRateProviderAsync {

    void processRate(String ccyPair, ExchangeRateReceiver receiver);
}
