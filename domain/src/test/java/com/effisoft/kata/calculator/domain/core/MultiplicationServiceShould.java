package com.effisoft.kata.calculator.domain.core;

import org.junit.Assert;
import org.junit.Test;

public class MultiplicationServiceShould {

    @Test
    public void close_all_process_input_streams_properly() {
        var service = new MultiplicationService();
        service.compute(12, 2);
        Assert.assertEquals(0, WrappedInputStream.countOpenStreams());
    }
}
