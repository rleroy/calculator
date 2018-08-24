package com.effisoft.kata.domain.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class WrappedInputStream extends InputStream {

    private final static List<WrappedInputStream> ALL_INSTANCES = new ArrayList<>();

    static long countOpenStreams() {
        return ALL_INSTANCES.stream()
            .filter(WrappedInputStream::isOpen)
            .count();
    }

    private InputStream input;
    private boolean open = true;

    WrappedInputStream(InputStream inputStream) {
        this.input = inputStream;
        ALL_INSTANCES.add(this);
    }

    @Override
    public int read() throws IOException {
        return input.read();
    }

    @Override
    public void close() throws IOException {
        open = false;
        input.close();
    }

    private boolean isOpen() {
        return this.open;
    }
}
