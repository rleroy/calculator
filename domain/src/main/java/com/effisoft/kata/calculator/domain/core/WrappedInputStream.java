package com.effisoft.kata.calculator.domain.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

class WrappedInputStream extends InputStream {

    private final static AtomicInteger OPEN_STREAM_COUNT = new AtomicInteger(0);

    static long countOpenStreams() {
        return OPEN_STREAM_COUNT.get();
    }

    private InputStream input;

    WrappedInputStream(InputStream inputStream) {
        this.input = inputStream;
        OPEN_STREAM_COUNT.incrementAndGet();
    }

    @Override
    public int read() throws IOException {
        return input.read();
    }

    @Override
    public void close() throws IOException {
        input.close();
        OPEN_STREAM_COUNT.decrementAndGet();
    }
}
