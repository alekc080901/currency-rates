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

    @GetMapping("/rates/{from}-{to}")
    public ExchangeRate convertCurrency(@PathVariable String from, @PathVariable String to) {
        double rate = converter.calculate(from, to);
        return new ExchangeRate(from, to, rate);
    }
}
