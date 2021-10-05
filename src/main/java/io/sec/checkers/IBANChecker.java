package io.sec.checkers;

import nl.garvelink.iban.IBAN;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marcin Bukowiecki
 */
public class IBANChecker implements Checker {

    private static final Logger log = Logger.getLogger(IBANChecker.class.getCanonicalName());

    public boolean isSensitive(@NotNull String value) {
        try {
            IBAN.parse(value);
            log.log(Level.SEVERE, "Got sensitive data type: " + getCheckerName());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public @NotNull String getCheckerName() {
        return "IBAN";
    }
}
