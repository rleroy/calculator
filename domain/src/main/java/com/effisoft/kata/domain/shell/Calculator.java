package com.effisoft.kata.domain.shell;

public class Calculator implements Runnable {

    private CalculatorInput input;
    private CalculatorOutput output;
    private CalculatorStorage storage;

    Throwable error = null;

    public Calculator(CalculatorInput input, CalculatorOutput output, CalculatorStorage storage) {
        this.input = input;
        this.output = output;
        this.storage = storage;
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
        String[] operators = operation.split("\\+");
        return String.valueOf(
            Integer.valueOf(operators[0]) + Integer.valueOf(operators[1])
        );
    }
}
