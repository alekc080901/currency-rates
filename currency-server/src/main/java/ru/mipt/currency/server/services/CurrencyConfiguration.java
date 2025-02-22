package ru.mipt.currency.server.services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.mipt.currency.server.models.ExchangeRate;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.currency")
public class CurrencyConfiguration {

    private List<ExchangeRate> exchangeRate;
}
