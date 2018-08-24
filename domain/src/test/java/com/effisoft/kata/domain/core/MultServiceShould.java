package com.effisoft.kata.domain.core;

import org.junit.Assert;
import org.junit.Test;

public class MultServiceShould {

    @Test
    public void close_all_process_input_streams_properly() {
        MultService service = new MultService();
        service.compute(12, 2);
        Assert.assertEquals(0, service.countOpenStreams());
    }
}
