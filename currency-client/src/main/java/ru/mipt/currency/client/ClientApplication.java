package ru.mipt.currency.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);
        ExchangeRateProvider exchangeRateProvider = context.getBean(ExchangeRateProvider.class);
    }

}
