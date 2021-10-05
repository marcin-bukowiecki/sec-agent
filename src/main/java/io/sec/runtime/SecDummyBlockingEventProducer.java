package io.sec.runtime;

import org.jetbrains.annotations.NotNull;

/**
 * @author Marcin Bukowiecki
 */
public class SecDummyBlockingEventProducer implements SecProducer {

    private final SecDummyBlockingEventConsumer secBlockingEventConsumer;

    public SecDummyBlockingEventProducer(SecDummyBlockingEventConsumer secBlockingEventConsumer) {
        this.secBlockingEventConsumer = secBlockingEventConsumer;
    }

    @Override
    public @NotNull SecConsumer getConsumer() {
        return secBlockingEventConsumer;
    }

    @Override
    public void start() {

    }

    @Override
    public void publishEvent(@NotNull String value) {
        final Thread thread = Thread.currentThread();
        secBlockingEventConsumer.add(thread, value);
    }
}
