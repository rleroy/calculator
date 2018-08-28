package com.effisoft.kata.calculator.domain.shell;

import com.effisoft.kata.calculator.domain.core.*;
import org.apache.log4j.Logger;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Calculator implements Runnable {

    private static Logger logger = Logger.getLogger(Calculator.class);

    private CalculatorInput input;
    private CalculatorOutput output;
    CalculatorStorage storage;

    Throwable error = null;
    Set<OperationService<Integer>> services;

    public Calculator(CalculatorInput input, CalculatorOutput output, CalculatorStorage storage) {
        this.input = input;
        this.output = output;
        this.storage = storage;

        OperationService<Integer> div = new OperationService<>("([0-9]*)/([0-9]*)") {
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
    }

    String compute(String operation) {
        String response;
        Optional<String> result = storage.retrieve(operation);
        if (result.isPresent()) {
            response = result.get();
        } else {
            response = this.computeOnServices(operation);
            storage.store(operation, response);
        }
        return response;
    }

    String computeOnServices(String operation) {
        String result = "???";

        for (OperationService<Integer> service : services) {
            Matcher matcher = service.matcher(operation);
            if (matcher.matches()) {
                try {
                    result = String.valueOf(
                        service.compute(
                            Integer.valueOf(matcher.group(1)),
                            Integer.valueOf(matcher.group(2))
                        )
                    );
                } catch (OperationException e) {
                    logger.error(e.getMessage(), e);
                }
                break;
            }
        }

        return result;
    }
}
