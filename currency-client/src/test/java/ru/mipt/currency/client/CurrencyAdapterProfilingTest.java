package ru.mipt.currency.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class CurrencyAdapterProfilingTest {

    private static final int MEASUREMENT_TIME_SECONDS = 6;
    private static final String SERVER_URL = "http://127.0.0.1:8080";

    private CurrencyAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new RestCurrencyAdapter(new RestTemplate(), WebClient.create(), SERVER_URL);
    }

    @Test
    void measureGetCurrencyOnSync() {
        System.out.println("Measure On Sync...");
        List<Long> executions = new ArrayList<>();
        LocalTime stopTime = LocalTime.now().plusSeconds(MEASUREMENT_TIME_SECONDS);
        while (LocalTime.now().isBefore(stopTime)) {
            long startTime = System.nanoTime();
            adapter.getCurrencySync("USDRUB");
            executions.add(System.nanoTime() - startTime);
        }
        printReport(executions);
    }

    @Test
    void measureGetCurrencyOnAsync() {
        System.out.println("Measure On Async...");
        List<Long> executions = new ArrayList<>();
        LocalTime stopTime = LocalTime.now().plusSeconds(MEASUREMENT_TIME_SECONDS);
        while (LocalTime.now().isBefore(stopTime)) {
            long startTime = System.nanoTime();
            adapter.getCurrencyAsync("USDRUB");
            executions.add(System.nanoTime() - startTime);
        }
        printReport(executions);
    }

    private void printReport(Collection<Long> measurements) {
        System.out.println("==========REPORT===========");
        System.out.println("Average latency: " + measurements.stream()
                .mapToDouble(Long::longValue)
                .map(nanos -> nanos / 1000_000_000)
                .average()
                .orElseThrow(() -> new RuntimeException("Could not get average latency"))
        );
        System.out.println("Throughput: " + ((double) measurements.size()) / MEASUREMENT_TIME_SECONDS);
        System.out.println("===========================\n");
    }
}