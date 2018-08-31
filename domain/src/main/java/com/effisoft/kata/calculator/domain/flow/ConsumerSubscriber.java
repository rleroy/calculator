package com.effisoft.kata.calculator.domain.flow;

import org.apache.log4j.Logger;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.function.Consumer;

public class ConsumerSubscriber<T> implements Subscriber<T> {

    private static Logger logger = Logger.getLogger(ConsumerSubscriber.class);

    private Subscription subscription;
    private Consumer<T> consumer;

    public ConsumerSubscriber(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        consumer.accept(item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        logger.error(t.getMessage(), t);
    }

    @Override
    public void onComplete() {
    }
}
