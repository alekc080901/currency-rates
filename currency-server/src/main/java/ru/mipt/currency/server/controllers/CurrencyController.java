package ru.mipt.currency.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.mipt.currency.server.models.ExchangeRate;
import ru.mipt.currency.server.services.CurrencyConverter;

@RestController
public class CurrencyController {

    private final CurrencyConverter converter;

    public CurrencyController(CurrencyConverter converter) {
        this.converter = converter;
    }

    @GetMapping("/rates/{ccyPair}")
    public ExchangeRate convertCurrency(@PathVariable String ccyPair) {
        double rate = converter.calculate(ccyPair);
        return new ExchangeRate(ccyPair, rate);
    }
}
