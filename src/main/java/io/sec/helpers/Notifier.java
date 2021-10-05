package io.sec.helpers;

import io.sec.checkers.Checker;
import org.jetbrains.annotations.NotNull;

/**
 * @author Marcin Bukowiecki
 */
public interface Notifier {

    void notify(@NotNull Checker checker,
                @NotNull String sensitiveData,
                StackTraceElement[] stackTraceElements);
}
