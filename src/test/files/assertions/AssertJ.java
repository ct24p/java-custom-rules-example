package checks.assertions;

import java.util.List;
import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.assertj.core.api.Fail;
import org.assertj.core.api.JUnitSoftAssertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Rule;
import org.junit.Test;

public abstract class AssertJ {

    interface Visitor {
        void visit(Object object);
    }

    class VisitorHandler {
        VisitorHandler(Object object, Visitor visitor) {
            visitor.visit(object);
        }
    }

    private final SoftAssertions softAssert = new SoftAssertions();

    @Rule
    public final JUnitSoftAssertions softAssertRuleRule = new JUnitSoftAssertions();

    @Test
    public void thenExceptionIsThrownBy() {
        org.assertj.core.api.BDDAssertions.thenException().isThrownBy(() -> System.out.println("b"));
    }

    @Test
    public void containsNoAssertions() { // Noncompliant
    }

    @Test
    public void softAssertThat() {
        softAssert.assertThat(5).isLessThan(3);
    }

    @Test
    public void softAssertAll() {
        softAssert.assertAll();
    }

    @Test
    public void softAssertRuleAssertThat() {
        softAssertRule.assertThat(5).isLessThan(3);
    }

    @Test
    public void assertionsAssertThat() {
        Assertions.assertThat(true);
    }

    @Test
    public void assertionsAssertThatMethodRef() {
        new java.util.ArrayList<Boolean>().forEach(Assertions::assertThat);
    }

    @Test
    public void assertionsFail() {
        Assertions.fail("a");
    }

    @Test
    public void assertionsFailException() {
        Assertions.fail("a", new IllegalArgumentException());
    }

    @Test
    public void assertionsFailBecause() {
        Assertions.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test
    public void failFail() {
        Fail.fail("failure");
    }

    @Test
    public void failFailBecause() {
        Fail.failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
    }

    @Test
    public void failShouldHaveThrown() {
        Fail.shouldHaveThrown(IllegalArgumentException.class);
    }

    @Test
    public void assertionsInAnonymousClass() {
        new VisitorHandler(null, new Visitor() {
            @Override
            public void visit(Object object) {
                Assertions.fail("a");
            }
        });
    }

    @Test
    public void noAssertionInAnonymousClass() { // Noncompliant
        new VisitorHandler(null, new Visitor() {
            @Override
            public void visit(Object object) {
                java.util.Objects.isNull(object);
            }
        });
    }

    @Test
    public void assertionsInLambda() {
        new VisitorHandler(null, object -> Assertions.fail("a"));
    }

    @Test
    public void assertionInHelperMethod() {
        helperMethod(true);
    }

    @Test
    public void assertionInHelperMethodChain() {
        intermediateHelperMethod(true);
    }

    @Test
    public void assertionInHelperMethodTwo() {
        helperMethodWithCustomAssertionMethod();
    }

    @Test
    public void assertionInStaticHelperMethod() {
        staticHelperMethod();
    }

    @Test
    public void assertionInHelperMethodAs_reference() {
        new java.util.ArrayList<Boolean>().forEach(this::helperMethod);
        new java.util.ArrayList<Boolean>().forEach(this::helperMethod_noAssert);
    }

    @Test
    public void noAssertionInHelperMethod() { // Noncompliant
        helperMethod_noAssert(true);
    }

    @Test
    public void noAssertionInHelperMethodAs_reference() { // Noncompliant
        new java.util.ArrayList<Boolean>().forEach(this::helperMethod_noAssert);
    }

    @Test
    public void assertionInExternalStaticMethod() { // Noncompliant
        // FP as rule currently cannot resolve cross-files custom assert methods
        org.sonarsource.helper.AssertionsHelper.customAssertion();
    }

    @Test
    public void assertionInExternalMethod() { // Noncompliant
        // FP as rule currently cannot resolve cross-files custom assert methods
        new org.sonarsource.helper.AssertionsHelper().customInstanceAssertion();
    }

    @Test
    public void assertionInWhitelistedExternalStaticMethod() {
        org.sonarsource.helper.AssertionsHelper.customAssertionAsRuleParameter(true);
    }

    @Test
    public void assertionInWhitelistedExternalMethod() {
        new org.sonarsource.helper.AssertionsHelper().customInstanceAssertionAsRuleParameter();
    }

    @Test
    public void assertionInWhitelistedExternalMethodAs_reference() {
        new java.util.ArrayList<Boolean>().forEach(org.sonarsource.helper.AssertionsHelper::customAssertionAsRuleParameter);
    }

    @Test
    public void assertionMethod_referenceInHelperMethod() {
        helperMethodWithCustomAssertionMethod_reference();
    }

    abstract boolean booleanMethod();

    abstract Object[] arrayMethod();

    abstract java.util.List<String> listStringMethod();

    @Test
    public void bddAssertionsWith_boolean() { // Compliant
        BDDAssertions.then(booleanMethod()).isTrue();
    }

    @Test
    public void bddAssertionsWithArrayAssert() { // Cmpliant
        BDDAssertions.then(arrayMethod()).contains("A", "B");
    }

    @Test
    public void bddAssertionsWithList() { // Compliant
        BDDAssertions.then(listStringMethod()).contains("Bob", "Alice").doesNotContain("Maurice");
    }

    @Test
    public void bddAssertionsSplitWithIntermediateAssertion() { // Compliant
        AbstractListAssert<?, ? extends List<? extends String>, String, ?> thenResult = BDDAssertions.then(listStringMethod());
        thenResult.contains("Bob", "Alice").doesNotContain("Maurice");
    }

    @Test
    public void bddAssertionsExampleWithoutAssertion() { // Noncompliant - nothing is asserted here
        BDDAssertions.then(listStringMethod());
    }

    public void intermediateHelperMethod(boolean booleanValue) {
        if (booleanValue) {
            intermediateHelperMethod(!booleanValue);
        }
        helperMethod(booleanValue);
    }

    public void helperMethod(boolean expected) {
        Assertions.assertThat(expected);
    }

    public void helperMethodWithCustomAssertionMethod() {
        org.sonarsource.helper.AssertionsHelper.customAssertionAsRuleParameter(true);
    }

    public void helperMethodWithCustomAssertionMethod_reference() {
        new java.util.ArrayList<Boolean>().forEach(org.sonarsource.helper.AssertionsHelper::customAssertionAsRuleParameter);
    }

    public static void staticHelperMethod() {
        new java.util.ArrayList<Boolean>().forEach(Assertions::assertThat);
        new java.util.ArrayList<Boolean>().forEach(java.util.Objects::isNull);
    }

    public void helperMethodNoAssert(boolean expected) {
        new java.util.ArrayList<Boolean>().forEach(java.util.Objects::isNull);
    }
}
