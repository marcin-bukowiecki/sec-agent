package io.sec.utils;

import io.sec.checkers.Checker;
import io.sec.dto.CustomPatterns;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Marcin Bukowiecki
 */
class PatternLoaderTest {

    private final PatternLoader SUT = PatternLoader.getInstance();

    @Test
    public void test_loadPatterns_1() {
        final CustomPatterns customPatterns = SUT.loadPatterns("./src/test/resources/patterns/p1.json");
        Assertions.assertEquals(1, customPatterns.getCustomPatternList().size());
        Assertions.assertEquals("CVV", customPatterns.getCustomPatternList().get(0).getPatternName());

        final List<Checker> checkers = SUT.createCheckers(customPatterns.getCustomPatternList());
        Assertions.assertEquals(1, checkers.size());
        Assertions.assertEquals("CVV", checkers.get(0).getCheckerName());
    }
}
