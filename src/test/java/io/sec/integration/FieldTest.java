package io.sec.integration;

import io.sec.AbstractTestCase;
import io.sec.checkers.SecRuntimeChecker;
import io.sec.runtime.SecDummyBlockingEventConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sandbox.Test3;
import sandbox.Test4;

/**
 * @author Marcin Bukowiecki
 */
@Disabled("Not working when invoking all tests.")
public class FieldTest extends AbstractTestCase {

    @Test
    public void test_fields_1() {
        retransformClass(Test3.class);
        Test3.test();

        final SecDummyBlockingEventConsumer consumer = (SecDummyBlockingEventConsumer) SecRuntimeChecker.getInstance()
                .getSecProducer()
                .getConsumer();
        final String v = consumer.getResult().get(Thread.currentThread().getId());
        Assertions.assertEquals("PL75109024026978617931837585", v);
    }

    @Test
    public void test_fields_2() {
        retransformClass(Test4.class);
        Test4.test();

        final SecDummyBlockingEventConsumer consumer = (SecDummyBlockingEventConsumer) SecRuntimeChecker.getInstance()
                .getSecProducer()
                .getConsumer();
        final String v = consumer.getResult().get(Thread.currentThread().getId());
        Assertions.assertNull(v);
    }
}
