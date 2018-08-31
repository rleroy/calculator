package com.effisoft.kata.calculator.domain.core;

public class Operation {
    private String input;
    private String output;

    Operation(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
