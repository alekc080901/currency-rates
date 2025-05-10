package ru.mipt.currency.client.sync;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mipt.common.dto.ExchangeRate;

import java.util.Optional;

public interface ExchangeRateProvider {

    Optional<ExchangeRate> getRate(String ccyPair);
}
