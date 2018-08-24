package com.effisoft.kata.domain.shell;

import com.effisoft.kata.domain.core.AddiService;
import com.effisoft.kata.domain.core.OperationException;
import com.effisoft.kata.domain.core.OperationService;
import com.effisoft.kata.domain.core.SubsService;
import org.apache.log4j.Logger;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Calculator implements Runnable {

    private static Logger logger = Logger.getLogger(Calculator.class);

    private CalculatorInput input;
    private CalculatorOutput output;
    private CalculatorStorage storage;

    Throwable error = null;
    private Set<OperationService<Integer>> services;

    public Calculator(CalculatorInput input, CalculatorOutput output, CalculatorStorage storage) {
        this.input = input;
        this.output = output;
        this.storage = storage;

        this.services = Stream.of(
            new AddiService(),
            new SubsService()
        ).collect(Collectors.toSet());
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
