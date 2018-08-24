package com.effisoft.kata.domain.core;

import java.io.IOException;

public class OperationException extends Exception {
    OperationException(String message) {
        super(message);
    }

    OperationException(IOException exception) {
        super(exception);
    }
}
