package io.sec.checkers;

import org.jetbrains.annotations.NotNull;

/**
 * @author Marcin Bukowiecki
 */
public interface Checker {

    boolean isSensitive(@NotNull String value);

    @NotNull String getCheckerName();
}
