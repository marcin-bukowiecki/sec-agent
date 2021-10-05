package io.sec.checkers;

import io.sec.utils.Patterns;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

/**
 * @author Marcin Bukowiecki
 */
public class ExpirationDateChecker implements Checker {

    @Override
    public boolean isSensitive(@NotNull String value) {
        for (Pattern p : Patterns.getExpirationDatePatterns()) {
            if (p.matcher(value).matches()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public @NotNull String getCheckerName() {
        return "Card Expiration Date";
    }
}
