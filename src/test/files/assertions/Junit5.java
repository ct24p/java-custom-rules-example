package checks.assertions;

import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class Junit5Test {

    @Test
    void testMethodParent() {
        assertTrue(true);
    }
}

class ATest extends Junit5Test {
    @Override
    void testMethodParent() { // Ok - not considered as test method as it is overridden
    }

    @Test
    void test1NoAssertion() { // Noncompliant {{Add at least one assertion to this test case.}}
    }

    @Test
    void test1WithAssertion() {
        assertTrue(true);
    }

    @ParameterizedTest
    @ValueSource(strings = { "a", "b" })
    void test2NoAssertion(String val) { // Noncompliant
    }

    @ParameterizedTest
    @ValueSource(strings = { "a", "b" })
    void test2WithAssertion(String val) {
        Assertions.fail("message");
    }

    @RepeatedTest(3)
    void test3NoAssertion() { // Noncompliant
    }

    @RepeatedTest(3)
    void test3WithAssertion() {
        Assertions.assertEquals(1, 2);
    }

    @TestFactory
    Collection<DynamicTest> test4NoAssertion() { // Noncompliant
        return Arrays.asList();
    }

    @TestFactory
    Collection<DynamicTest> test4WithAssertion() {
        return Arrays.asList(dynamicTest("1", () -> assertTrue(true)), dynamicTest("2", () -> assertTrue(false)));
    }

    @TestTemplate
    @ExtendWith(CustomStringContextProvider.class)
    void test5NoAssertion(String providedString) { // Noncompliant
    }

    @TestTemplate
    @ExtendWith(CustomStringContextProvider.class)
    void test5WithAssertion(String providedString) {
        bar();
    }

    void bar() {
        Assertions.assertThrows(null, null);
    }

    interface CustomStringContextProvider extends Extension {
    }
}
