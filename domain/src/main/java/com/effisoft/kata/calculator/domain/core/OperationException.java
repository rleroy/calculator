package com.effisoft.kata.calculator.domain.core;

public class OperationException extends Exception {
    OperationException(String message) {
        super(message);
    }

    OperationException(Exception exception) {
        super(exception);
    }
}
