package com.effisoft.kata.domain.core;

public class AdditionService extends OperationService<Integer> {

    public AdditionService() {
        super("([0-9]*)\\+([0-9]*)");
    }

    @Override
    public Integer compute(Integer val1, Integer val2) {
        return val1 + val2;
    }
}
