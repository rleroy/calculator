package com.effisoft.kata.domain.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class OperationService<T> {

    private Pattern pattern;

    OperationService(String regex) {
        pattern = Pattern.compile(regex);
    }

    public Matcher matcher(String operation) {
        return pattern.matcher(operation);
    }

    public abstract T compute(T val1, T val2) throws OperationException;

}
