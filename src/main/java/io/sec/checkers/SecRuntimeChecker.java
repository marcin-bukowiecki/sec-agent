package io.sec.checkers;

import io.sec.SecContext;
import io.sec.helpers.Notifier;
import io.sec.runtime.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Marcin Bukowiecki
 */
public enum SecRuntimeChecker {

    INSTANCE;

    private final List<Checker> checkers = new ArrayList<>();

    private final List<Notifier> notifiers = new ArrayList<>();

    private SecProducer secProducer;

    public void init(@NotNull SecContext secContext) {
        if (secContext.isBlocking()) {
            secProducer = new SecDummyBlockingEventProducer(new SecDummyBlockingEventConsumer());
        } else {
            secProducer = new SecAsyncEventProducer(new SecAsyncEventConsumer(this));
        }
        secProducer.start();
    }

    public @NotNull SecProducer getSecProducer() {
        assert secProducer != null : "Must be noty null";
        return secProducer;
    }

    public static @Nullable String isSensitivePushAsync(@Nullable String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        final SecRuntimeChecker secRuntimeChecker = getInstance();
        secRuntimeChecker.secProducer.publishEvent(value);

        return value;
    }

    public static @Nullable String isSensitive(@Nullable String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }

        final Thread thread = Thread.currentThread();
        final SecRuntimeChecker runtimeChecker = getInstance();
        runtimeChecker.getCheckers().forEach(checker -> {
            if (checker.isSensitive(value)) {
                runtimeChecker.getNotifiers()
                        .forEach(notifier -> notifier.notify(checker, value, thread.getStackTrace()));
            }
        });

        return value;
    }

    public void registerChecker(@NotNull Checker checker) {
        checkers.add(checker);
    }

    public void registerNotifier(@NotNull Notifier notifier) {
        notifiers.add(notifier);
    }

    public @NotNull List<@NotNull Notifier> getNotifiers() {
        return Collections.unmodifiableList(notifiers);
    }

    public @NotNull List<@NotNull Checker> getCheckers() {
        return Collections.unmodifiableList(checkers);
    }

    public static @NotNull SecRuntimeChecker getInstance() {
        return INSTANCE;
    }

    public void registerCheckers(Checker ...checkers) {
        this.checkers.addAll(List.of(checkers));
    }

    public void registerCheckers(List<Checker> checkers) {
        this.checkers.addAll(checkers);
    }
}
