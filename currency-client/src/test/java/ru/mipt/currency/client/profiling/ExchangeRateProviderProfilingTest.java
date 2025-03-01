package ru.mipt.currency.client.profiling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mipt.currency.client.ExchangeRateProvider;
import ru.mipt.currency.client.ExchangeRateProviderAsync;
import ru.mipt.currency.server.Utils;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@SpringBootTest
class ExchangeRateProviderProfilingTest {

    private static final int MEASUREMENT_TIME_SECONDS = 5;

    @Autowired
    private ExchangeRateProvider adapterSync;
    @Autowired
    private ExchangeRateProviderAsync adapterAsync;

    @Test
    void measureGetCurrencyOnSync() {
        measure((func) -> {
                    adapterSync.getRate("USDRUB");
                    func.run();
                },
                "Measuring Sync...");
    }

    @Test
    void measureGetCurrencyOnAsync() {
        measure((func) -> adapterAsync.processRate("USDRUB", (er) -> func.run()),
                "Measuring Async...");
    }

    private void measure(Consumer<Runnable> measuredOperation, String measureLabel) {
        System.out.println(measureLabel + "\n");
        List<Long> executions = new ArrayList<>();
        LocalTime start = LocalTime.now();
        while (LocalTime.now().isBefore(start.plusSeconds(MEASUREMENT_TIME_SECONDS))) {
            long startTime = System.nanoTime();
            mockActivity();
            measuredOperation.accept(() -> {
                executions.add(System.nanoTime() - startTime);
            });
            mockActivity();
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

    private void mockActivity() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException("Sleep interrupted", e);
        }
    }
}