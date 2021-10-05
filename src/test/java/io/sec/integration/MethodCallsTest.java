package io.sec.integration;

import io.sec.AbstractTestCase;
import io.sec.checkers.SecRuntimeChecker;
import io.sec.runtime.SecDummyBlockingEventConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sandbox.Test1;
import sandbox.Test2;

/**
 * @author Marcin Bukowiecki
 */
@Disabled("Not working when invoking all tests.")
public class MethodCallsTest extends AbstractTestCase {

    @Test
    public void test_methodReturn_1() {
        retransformClass(Test1.class);
        final Test1 test1 = new Test1();
        test1.test();

        final SecRuntimeChecker runtimeChecker = SecRuntimeChecker.getInstance();
        final SecDummyBlockingEventConsumer consumer = (SecDummyBlockingEventConsumer) runtimeChecker.getSecProducer().getConsumer();
        final String v = consumer.getResult().get(Thread.currentThread().getId());
        Assertions.assertEquals("PL75109024026978617931837585", v);
    }

    @Test
    public void test_methodReturn_2() throws Exception {
        retransformClass(Test2.class);
        final Class<?> aClass = Class.forName("sandbox.Test2");
        aClass.getDeclaredMethod("test").invoke(null);

        final SecRuntimeChecker runtimeChecker = SecRuntimeChecker.getInstance();
        final SecDummyBlockingEventConsumer consumer = (SecDummyBlockingEventConsumer) runtimeChecker.getSecProducer().getConsumer();
        final String v = consumer.getResult().get(Thread.currentThread().getId());
        Assertions.assertEquals("PL75109024026978617931837585", v);
    }
}
