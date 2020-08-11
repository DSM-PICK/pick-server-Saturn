package com.dsm.pick;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestClassTest {
    private TestClass testClass = new TestClass();

    @Test
    void test() {
        int result = testClass.test(1);
        Assertions.assertEquals(result, 2);

        int result2 = testClass.test(2);
        Assertions.assertEquals(result2, 3);

        int result3 = testClass.test(3);
        Assertions.assertEquals(result3, 4);

        int result4 = testClass.test(4);
        Assertions.assertEquals(result4, 0);
    }
}