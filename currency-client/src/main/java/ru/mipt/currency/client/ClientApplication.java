package ru.mipt.currency.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.mipt.currency.client.sync.ExchangeRateProvider;

@SpringBootApplication
@EnableScheduling
public class ClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ClientApplication.class, args);
        ExchangeRateProvider exchangeRateProvider = context.getBean(ExchangeRateProvider.class);
    }

}
