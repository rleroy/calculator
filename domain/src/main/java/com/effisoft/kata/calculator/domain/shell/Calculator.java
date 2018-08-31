package com.effisoft.kata.calculator.domain.shell;

import com.effisoft.kata.calculator.domain.core.*;
import com.effisoft.kata.calculator.domain.flow.ConsumerSubscriber;
import com.effisoft.kata.calculator.domain.core.OperationPublisher;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.SECONDS;

public class Calculator implements Runnable {

    private static Logger logger = Logger.getLogger(Calculator.class);

    private CalculatorInput input;
    private CalculatorOutput output;
    CalculatorStorage storage;

    Throwable error = null;
    Set<OperationService<Integer>> services;
    SubmissionPublisher<String> publisher;

    public Calculator(CalculatorInput input, CalculatorOutput output, CalculatorStorage storage) {
        this.input = input;
        this.output = output;
        this.storage = storage;

        var div = new OperationService<Integer>("([0-9]*)/([0-9]*)") {
            @Override
            public Integer compute(Integer val1, Integer val2) {
                return val1 / val2;
            }
        };

        this.services = Set.of(
            new AdditionService(),
            new SubtractionService(),
            new MultiplicationService(),
            div
        );

        publisher = new SubmissionPublisher<>();
        var storageSubscriber = new ConsumerSubscriber<Operation>(
            item -> storage.store(item.getInput(), item.getOutput())
        );

        this.services
            .forEach(service -> {
                var operationPublisher = new OperationPublisher(service);
                publisher.subscribe(operationPublisher);
                operationPublisher.subscribe(storageSubscriber);
            });
    }

    @Override
    public void run() {
        try {
            loopOnCommands();
        } catch (Throwable error) {
            this.error = error;
        }
    }

    private void loopOnCommands() {
        String command;
        while (!"exit".equals(command = input.read())) {
            output.write(compute(command));
        }
        publisher.close();
    }

    String compute(String operation) {
        var result = storage.retrieve(operation);
        if (!result.isPresent()) {
            computeOnServices(operation);
            waitUntilAtMost(
                () -> storage.retrieve(operation).isPresent(),
                Duration.of(5, SECONDS)
            );
        }
        return storage.retrieve(operation).orElse("???");
    }

    void computeOnServices(String operation) {
        publisher.submit(operation);
    }

    private void waitUntilAtMost(Supplier<Boolean> test, Duration duration) {
        long start = System.currentTimeMillis();
        while (
            !test.get()
            && (System.currentTimeMillis() - start) < duration.toMillis()
        ) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
