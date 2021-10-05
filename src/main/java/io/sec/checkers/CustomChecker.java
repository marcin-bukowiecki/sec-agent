package io.sec.checkers;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * @author Marcin Bukowiecki
 */
public class CustomChecker implements Checker {

    private final String checkerName;

    private final Pattern pattern;

    public CustomChecker(@NotNull String checkerName, @NotNull String pattern) {
        this.checkerName = checkerName;
        this.pattern = Pattern.compile(pattern);
    }

    @Override
    public boolean isSensitive(@NotNull String value) {
        return pattern.matcher(value).matches();
    }

    @Override
    public @NotNull String getCheckerName() {
        return checkerName;
    }
}
