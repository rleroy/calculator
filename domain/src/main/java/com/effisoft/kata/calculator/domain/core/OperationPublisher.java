package com.effisoft.kata.calculator.domain.core;

import com.effisoft.kata.calculator.domain.flow.ProcessorPublisher;
import org.apache.log4j.Logger;

public class OperationPublisher extends ProcessorPublisher<String, Operation> {

    private static Logger logger = Logger.getLogger(OperationPublisher.class);

    private OperationService<Integer> service;

    public OperationPublisher(OperationService<Integer> service) {
        this.service = service;
    }

    @Override
    public Operation process(String input) {
        Operation response = null;
        var matcher = service.matcher(input);
        if (matcher.matches()) {
            try {
                var output = String.valueOf(
                    service.compute(
                        Integer.valueOf(matcher.group(1)),
                        Integer.valueOf(matcher.group(2))
                    )
                );
                response = new Operation(input, output);
            } catch (OperationException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return response;
    }
}
