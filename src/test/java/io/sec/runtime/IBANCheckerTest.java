package io.sec.runtime;

import io.sec.checkers.IBANChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Marcin Bukowiecki
 */
class IBANCheckerTest {

    private final IBANChecker ibanChecker = new IBANChecker();

    @Test
    public void test_checkIBAN_1() {
        Assertions.assertFalse(ibanChecker.isSensitive("12"));
    }

    @Test
    public void test_checkIBAN_2() {
        Assertions.assertTrue(ibanChecker.isSensitive("NL66ABNA6221094275"));
    }

    @Test
    public void test_checkIBAN_3() {
        Assertions.assertTrue(ibanChecker.isSensitive("PL17109024028461998494866144"));
    }
}
