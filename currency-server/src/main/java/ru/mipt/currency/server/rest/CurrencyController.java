package ru.mipt.currency.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mipt.currency.server.models.ExchangeRate;
import ru.mipt.currency.server.services.CurrencyConverter;

import java.util.Collection;
import java.util.List;

@RestController
public class CurrencyController {

    private final CurrencyConverter converter;

    public CurrencyController(CurrencyConverter converter) {
        this.converter = converter;
    }

    @GetMapping("/rates")
    public List<ExchangeRate> convertCurrency(@RequestParam Collection<String> ccyPair) {
        return ccyPair.stream()
                .map(pair -> new ExchangeRate(
                        pair,
                        converter.calculate(pair))
                ).toList();
    }
}
