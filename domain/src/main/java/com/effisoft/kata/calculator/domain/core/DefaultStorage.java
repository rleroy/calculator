package com.effisoft.kata.calculator.domain.core;

import com.effisoft.kata.calculator.domain.shell.CalculatorStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DefaultStorage implements CalculatorStorage {

    private static final DefaultStorage INSTANCE = new DefaultStorage();

    public static DefaultStorage getInstance() {
        return INSTANCE;
    }

    private final Map<String, String> memory = new HashMap<>();

    @Override
    public void store(String operation, String result) {
        memory.put(operation, result);
    }

    @Override
    public Optional<String> retrieve(String operation) {
        return Optional.ofNullable(memory.get(operation));
    }

    public void reset() {
        this.memory.clear();
    }
}
