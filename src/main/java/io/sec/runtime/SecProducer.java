package io.sec.runtime;

import org.jetbrains.annotations.NotNull;

/**
 * @author Marcin Bukowiecki
 */
public interface SecProducer {

    @NotNull SecConsumer getConsumer();

    void start();

    void publishEvent(@NotNull String value);
}
