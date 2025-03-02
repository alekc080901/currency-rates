package ru.mipt.currency.client.sync;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mipt.common.dto.ExchangeRate;

@FeignClient("currency-server")
public interface CurrencyServerFeignClient {

    @GetMapping(value = "/rates")
    ExchangeRate[] getRate(@RequestParam("ccyPair") String ccyPair);
}
