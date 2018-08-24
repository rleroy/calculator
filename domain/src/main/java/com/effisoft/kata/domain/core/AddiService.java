package com.effisoft.kata.domain.core;

public class AddiService extends OperationService<Integer> {

    public AddiService() {
        super("([0-9]*)\\+([0-9]*)");
    }

    @Override
    public Integer compute(Integer val1, Integer val2) {
        return val1 + val2;
    }
}
