package checks.assertions;

import java.util.List;
import javax.annotation.Nullable;
//import junit.framework.TestCase;
import org.junit.Rule;
//import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.junit.Assert;

abstract class Junit4Test {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public org.junit.rules.ErrorCollector errorCollector = new org.junit.rules.ErrorCollector();

    private static int VAL = staticAndNotAUnitTest();

    private static int staticAndNotAUnitTest() {
        return 0;
    }

    @org.junit.Test
    public void containsNoAssertions() { // Noncompliant [[sc=17;ec=37]] {{Add at least one assertion to this test case.}}
    }

    @Nullable
    public org.junit.Test notAUnitTest() {
        containsNoAssertions();
        return null;
    }

    @org.junit.Test(timeout = 0L)
    public void containsNoAssertionsButExceptions() { // Noncompliant
        throw new IllegalStateException("message");
    }

    @org.junit.Test
    public abstract void abstractUnitTest();

    @org.junit.Test
    public void junitAssertEquals() {
        Assert.assertEquals(true, true);
    }

    @org.junit.Test
    public void junitAssertTrue() {
        Assert.assertTrue(true);
        Assert.assertTrue(true); // Coverage
    }

    @org.junit.Test
    public void junitAssertThat() {
        Assert.assertThat("aaa", org.junit.matchers.JUnitMatchers.containsString("a"));
    }

    @org.junit.Test
    public void junitAssertThatGeneric() {
        Assert.<String> assertThat("aaa", org.junit.matchers.JUnitMatchers.containsString("a"));
    }

    @org.junit.Test
    public void festAssertFail() {
        org.fest.assertions.Fail.fail("foo");
    }

    @org.junit.Test
    public void festAssertMethodReference() {
        new java.util.ArrayList<org.fest.assertions.GenericAssert>().forEach(org.fest.assertions.GenericAssert::isNull);
    }

    @org.junit.Test
    public void festAssertHelperMethod() {
        helperFestAssert();
    }

    @org.junit.Test
    public void festAssertHelperMethodReference() {
        helperFestAssertMethodReference();
    }

    public void helperFestAssert() {
        org.fest.assertions.Fail.fail("foo");
    }

    public void helperFestAssertMethodReference() {
        new java.util.ArrayList<org.fest.assertions.GenericAssert>().forEach(org.fest.assertions.GenericAssert::isNull);
    }

    @org.junit.Test
    public void festAssertThat() {
        org.fest.assertions.Assertions.assertThat(true);
    }

    @org.junit.Test
    public void festAssertThatEquals() {
        org.fest.assertions.Assertions.assertThat(true).isEqualTo(true);
    }

    @org.junit.Test
    public void junitRuleExpectedException() {
        thrown.expect(IllegalStateException.class);
        throw new IllegalStateException("message");
    }

    @org.junit.Test
    public void junitRuleExpectedExceptionMessage() {
        thrown.expectMessage("message");
        throw new IllegalStateException("message");
    }

    @org.junit.Test
    public void junitRuleErrorCollector() { // Compliant
        errorCollector.checkThat("123", org.hamcrest.CoreMatchers.equalTo("123"));
    }

    @org.junit.Test(expected = IllegalStateException.class)
    public void junitTestAnnotatedWithExpected() {
        throw new IllegalStateException("message");
    }

    @org.junit.Test
    public void mockitoAssertionVerify() {
        Mockito.verify(Mockito.mock(List.class)).clear();
    }

    @org.junit.Test
    public void mockitoAssertionVerifyTimes() {
        Mockito.verify(Mockito.mock(List.class), Mockito.times(0));
    }

    @org.junit.Test
    public void mockitoAssertionVerifyZeroInteractions() {
        Mockito.verifyZeroInteractions(Mockito.mock(List.class));
    }

    @org.junit.Test
    public void mockitoAssertionVerifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(Mockito.mock(List.class));
    }

    static abstract class AbstractTest {
        @org.junit.Test
        public abstract void unitTest();
    }

    static class ImplTest extends AbstractTest {
        @Override
        public void unitTest() { // Noncompliant
            // overridden test
        }
    }

    static class ExtendsTestCase extends junit.framework.TestCase {
        public void testContainsNoAssertions() { // Noncompliant
        }

        public void testAssertEquals() {
            assertEquals(true, true);
        }

        public void testFail() {
            fail("message");
        }
    }
}
