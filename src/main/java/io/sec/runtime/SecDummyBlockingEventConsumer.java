package io.sec.runtime;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marcin Bukowiecki
 */
public class SecDummyBlockingEventConsumer implements SecConsumer {

    private final Map<Long, String> result = new ConcurrentHashMap<>();

    public void add(@NotNull Thread thread, @NotNull String value) {
        result.put(thread.getId(), value);
    }

    public @NotNull Map<Long, String> getResult() {
        return Collections.unmodifiableMap(result);
    }

    public void remove(@NotNull Thread thread) {
        result.remove(thread.getId());
    }
}
