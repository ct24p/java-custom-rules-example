package checks.assertions;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Assert;

abstract class Junit4Test {

    @Test(expected = IllegalStateException.class)
    public void junit_test_annotated_with_expected() {
        throw new IllegalStateException("message");
    }

    @Test
    public void contains_no_assertions() { // Noncompliant [[sc=17;ec=39]] {{Add at least one assertion to this test case.}}
    }

    static class ExtendsTestCase extends junit.framework.TestCase {
        public void test_contains_no_assertions() { // Noncompliant
        }
    }

    @Test
    public void junit_assert_equals() {
        org.junit.Assert.assertEquals(true, true);
    }
}
