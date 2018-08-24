package com.effisoft.kata.infra.spi;

import com.effisoft.kata.domain.shell.CalculatorStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryStorage implements CalculatorStorage {

    private Map<String, String> memory = new HashMap<>();

    @Override
    public void store(String operation, String result) {
        memory.put(operation, result);
    }

    @Override
    public Optional<String> retrieve(String operation) {
        return Optional.ofNullable(memory.get(operation));
    }

}
