package io.sec.runtime;

import com.lmax.disruptor.EventFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

/**
 * @author Marcin Bukowiecki
 */
public class StringPushEvent {

    public static final EventFactory<StringPushEvent> EVENT_FACTORY = StringPushEvent::new;

    private String value;

    private WeakReference<Thread> threadWeakReference;

    public StringPushEvent() {
    }

    public StringPushEvent(@NotNull String value, @NotNull WeakReference<Thread> threadWeakReference) {
        this.value = value;
        this.threadWeakReference = threadWeakReference;
    }

    public void setValue(@NotNull String value) {
        this.value = value;
    }

    public void setThreadWeakReference(@NotNull WeakReference<Thread> threadWeakReference) {
        this.threadWeakReference = threadWeakReference;
    }

    public @Nullable String getValue() {
        return value;
    }

    public @Nullable WeakReference<Thread> getThreadWeakReference() {
        return threadWeakReference;
    }
}
