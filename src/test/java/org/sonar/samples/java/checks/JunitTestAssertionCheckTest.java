package org.sonar.samples.java.checks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.event.Level;
import org.sonar.api.testfixtures.log.LogTesterJUnit5;
import org.sonar.java.checks.verifier.CheckVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class JunitTestAssertionCheckTest {
    private static final String FILES_PATH = "src/test/files/assertions/";
    private static final String STR_MSG = "Unable to create a corresponding matcher for custom assertion method, "
            + "please check the format of the following symbol: ";
    private JunitTestAssertionCheck check = new JunitTestAssertionCheck();

    @RegisterExtension
    public LogTesterJUnit5 logTester = new LogTesterJUnit5().setLevel(Level.DEBUG);

    @BeforeEach
    void setup() {
        check.customAssertionMethods = "org.sonarsource.helper.AssertionsHelper$ConstructorAssertion#<init>,"
                +"org.sonarsource.helper.AssertionsHelper#customAssertionAsRule*,"
                + "org.sonarsource.helper.AssertionsHelper#customInstanceAssertionAsRuleParameter,blabla,bla# , #bla";
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "Junit4B"
    })/*
    @ValueSource(strings = {
      "Junit3",
      "Junit4",
      "Junit5",
      "Hamcrest",
      "Spring",
      "EasyMock",
      "Truth",
      "ReactiveX1",
      "ReactiveX2",
      "RestAssured",
      "RestAssured2",
      "Mockito",
      "JMock",
      "WireMock",
      "VertxJUnit4",
      "VertxJUnit5",
      "Selenide",
      "JMockit",
      "Awaitility",
      "AssertJ",
      "Custom"
    })*/
    void test(String framework) {
        CheckVerifier.newVerifier()
                .onFile(FILES_PATH + framework + ".java")
                .withCheck(check)
                .verifyIssues();
        assertThat(logTester.logs(Level.WARN)).contains(STR_MSG + "'blabla'", STR_MSG + "'bla# '", STR_MSG + "' #bla'");
    }

    @Test
    void testNonCompilingCode() {
        CheckVerifier.newVerifier()
                .onFile(FILES_PATH + "noncompiling/AssertJ.java")
                .withCheck(check)
                .verifyNoIssues();
    }

    @Test
    void testNoIssuesWithoutSemantic() {
        CheckVerifier.newVerifier()
                .onFile(FILES_PATH + "Junit3.java")
                .withCheck(check)
                .withoutSemantic()
                .verifyNoIssues();
    }

    @Test
    void testWithEmptyCustomAssertionMethods() {
        check.customAssertionMethods = "";
        CheckVerifier.newVerifier()
                .onFile(FILES_PATH + "Junit3.java")
                .withCheck(check)
                .verifyIssues();
        assertThat(logTester.logs(Level.WARN)).doesNotContain(STR_MSG + "''");
    }
}