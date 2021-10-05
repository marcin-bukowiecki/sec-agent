package io.sec.utils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Marcin Bukowiecki
 */
public class Patterns {

    public static class ExpirationDate {

        public static final Pattern PATTERN_1 = Pattern.compile("(?:0[1-9]|1[0-2])/[0-9]{2}");
    }

    public static class Card {

        /**
         * Visa, MasterCard, American Express, Diners Club, Discover, JCB
         */
        public static final Pattern PATTERN_1 = Pattern.compile("^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\\d{3})\\d{11})$");
    }

    public static List<Pattern> getCardNumberPatterns() {
        return List.of(
                Card.PATTERN_1
        );
    }

    public static List<Pattern> getExpirationDatePatterns() {
        return List.of(
                ExpirationDate.PATTERN_1
        );
    }
}
