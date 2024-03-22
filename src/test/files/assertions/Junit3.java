package checks.assertions;

import junit.framework.TestCase;

import javax.annotation.Nullable;

class Junit3Test extends TestCase {

    public void testContainsNoAssertions() { // Noncompliant
    }

    public void testAssertAssertTrue() {
        org.junit.Assert.assertTrue(true);
    }

    @Nullable
    public void notATest() {
    }

    public void testAssertionInHelperMethod() {
        helperMethod();
    }

    public void testAssertionInStaticHelperMethod() {
        staticHelperMethod();
    }

    public void testNoAssertionInHelperMethod() { // Noncompliant
        helperMethodNoAssert();
    }

    public void helperMethod() {
        org.junit.Assert.assertTrue(true);
        org.junit.Assert.assertTrue(false);
    }

    public static void staticHelperMethod() {
        org.junit.Assert.assertTrue(true);
        org.junit.Assert.assertTrue(false);
    }

    public void helperMethodNoAssert() {
        notATest();
    }
}
