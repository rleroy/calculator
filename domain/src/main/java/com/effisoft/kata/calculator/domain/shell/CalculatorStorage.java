package com.effisoft.kata.calculator.domain.shell;

import com.effisoft.kata.calculator.domain.core.DefaultStorage;

import java.util.Optional;

public interface CalculatorStorage {

    default void store(String operation, String result) {
        DefaultStorage.getInstance().store(operation, result);
    }

    default Optional<String> retrieve(String operation) {
        return DefaultStorage.getInstance().retrieve(operation);
    }

}
