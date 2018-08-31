package com.effisoft.kata.calculator.domain.flow;

import org.apache.log4j.Logger;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;

public abstract class ProcessorPublisher<T, R>
    extends SubmissionPublisher<R>
    implements Processor<T, R> {

    private static Logger logger = Logger.getLogger(ProcessorPublisher.class);

    private Subscription subscription;

    public abstract R process(T input);

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T input) {
        R response = process(input);
        if (response != null) {
            submit(response);
        }
        subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        logger.error(t.getMessage(), t);
    }

    @Override
    public void onComplete() {
        this.close();
    }
}
