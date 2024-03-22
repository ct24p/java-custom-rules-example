package checks.assertions;

//import org.junit.Test;
import org.junit.Assert;

abstract class Junit4Test {

    static class ExtendsTestCase extends junit.framework.TestCase {
        public void testContainsNoAssertions() { // Noncompliant
        }
    }
    
    @org.junit.Test(expected = IllegalStateException.class)
    public void junitTestAnnotatedWithExpected() {
        throw new IllegalStateException("message");
    }

    @org.junit.Test
    public void containsNoAssertions() { // Noncompliant [[sc=17;ec=37]] {{Add at least one assertion to this test case.}}
    }

    @org.junit.Test
    public void junitAssertEquals() {
        Assert.assertEquals(true, true);
    }
}
