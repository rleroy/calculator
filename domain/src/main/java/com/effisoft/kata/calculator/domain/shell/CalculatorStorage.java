package com.effisoft.kata.calculator.domain.shell;

import com.effisoft.kata.calculator.domain.core.DefaultStorage;

import java.util.Optional;

public interface CalculatorStorage {

    default void store(String operation, String result) {
        getDefaultInstance().store(operation, result);
    }

    default Optional<String> retrieve(String operation) {
        return getDefaultInstance().retrieve(operation);
    }

    private DefaultStorage getDefaultInstance() {
        return DefaultStorage.getInstance();
    }
}
