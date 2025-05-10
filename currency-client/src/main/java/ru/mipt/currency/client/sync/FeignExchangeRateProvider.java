package ru.mipt.currency.client.sync;

import org.springframework.stereotype.Component;
import ru.mipt.common.dto.ExchangeRate;

import java.util.Optional;

@Component
public class FeignExchangeRateProvider implements ExchangeRateProvider {

    private final CurrencyServerFeignClient currencyServer;

    public FeignExchangeRateProvider(CurrencyServerFeignClient currencyServer) {
        this.currencyServer = currencyServer;
    }

    @Override
    public Optional<ExchangeRate> getRate(String ccyPair) {
        try {
            return Optional.ofNullable(currencyServer.getRate(ccyPair))
                    .map(array -> array[0]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
