package io.sec.runtime;

import com.lmax.disruptor.EventHandler;
import io.sec.checkers.Checker;
import io.sec.checkers.SecRuntimeChecker;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

/**
 * @author Marcin Bukowiecki
 */
public class SecAsyncEventConsumer implements SecConsumer, EventHandler<StringPushEvent> {

    private final SecRuntimeChecker secRuntimeChecker;

    public SecAsyncEventConsumer(@NotNull SecRuntimeChecker secRuntimeChecker) {
        this.secRuntimeChecker = secRuntimeChecker;
    }

    @Override
    public void onEvent(StringPushEvent event, long seqID, boolean endOfBatch) throws Exception {
        if (event == null) {
            return;
        }

        final String value = event.getValue();
        final WeakReference<Thread> threadWeakReference = event.getThreadWeakReference();
        Thread thread;
        if (threadWeakReference != null && (thread = threadWeakReference.get()) != null) {
            for (Checker checker : secRuntimeChecker.getCheckers()) {
                if (value != null && checker.isSensitive(value)) {
                    secRuntimeChecker.getNotifiers().forEach(n -> n.notify(checker, value, thread.getStackTrace()));
                    break;
                }
            }
        }
    }
}
