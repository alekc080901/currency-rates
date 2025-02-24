package ru.mipt.currency.client.profiling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import ru.mipt.currency.client.CurrencyAdapter;
import ru.mipt.currency.client.RestCurrencyAdapter;
import ru.mipt.currency.server.Utils;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class CurrencyAdapterProfilingTest {

    private static final int MEASUREMENT_TIME_SECONDS = 10;
    private static final String SERVER_URL = "http://127.0.0.1:8080";

    private CurrencyAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new RestCurrencyAdapter(WebClient.create(), SERVER_URL);
    }

    @Test
    void measureGetCurrencyOnSync() {
        measure(() -> adapter.getCurrency("USDRUB").block());
    }

    @Test
    void measureGetCurrencyOnAsync() {
        measure(() -> adapter.getCurrency("USDRUB").subscribe());
    }

    private void measure(Runnable runnable) {
        System.out.println("Measuring...\n");
        List<Long> executions = new ArrayList<>();
        LocalTime start = LocalTime.now();
        while (LocalTime.now().isBefore(start.plusSeconds(MEASUREMENT_TIME_SECONDS))) {
            long startTime = System.nanoTime();
            runnable.run();
            executions.add(System.nanoTime() - startTime);
        }
        printReport(executions, Duration.between(start, LocalTime.now()));
    }

    private void printReport(Collection<Long> measurements, Duration duration) {
        double executionTime = duration.getSeconds() + duration.getNano() / 1_000_000_000.0;
        System.out.println("==========REPORT===========");
        System.out.println("Total requests: " + measurements.size());
        System.out.println("Execution time: " + Utils.round(executionTime, 2) + " seconds");
        System.out.println("Average latency: " + Utils.round(measurements.stream()
                .mapToDouble(Long::longValue)
                .map(nanos -> nanos / 1_000_000_000)
                .average()
                .orElseThrow(() -> new RuntimeException("Could not get average latency")), 3)
        );
        System.out.println("Throughput: " + Utils.round(((double) measurements.size()) / executionTime, 3));
        System.out.println("===========================\n");
    }
}