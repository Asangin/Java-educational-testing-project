# TestNG feature

> The goal is to provide examples of TestNG features

## Parallel test suites

This packages contains
classes [ParallelClassesOneTest.java](src/test/java/com/skryl/edu/pkg1/ParallelClassesOneTest.java),
[ParallelClassesTwoTest.java](src/test/java/com/skryl/edu/pkg2/ParallelClassesTwoTest.java) that will run in parallel
using this [test suite](src/test/resources/parallel-test-classes-suite.xml).

## Package scope test suite

In this [test suite](src/test/resources/package-scope-test-suite.xml) we can specify the package and all test classes
that are in this package will run. Noted! that package scope does not work recursively, so only test classes that belong
to specify package will run.

## Test LifeCycle

It is discovered that [package](src/test/java/com/skryl/edu/lifecycles) provides a simple view of which annotated method
is running in different circumstances.

Lifecycle execution sequence [LifeCycleTests.java](src/test/java/com/skryl/edu/lifecycles/LifeCycleTests.java)
Using multiple precondition methods and there execution sequence
in [MultipleLifecycleTests.java](src/test/java/com/skryl/edu/lifecycles/MultipleLifecycleTests.java)

## Parametrization test using the data provider

[DataProvider.java](src/test/java/com/skryl/edu/dataprovider/DataProvider.java) contains data that will use in the
test [DataProviderTests.java](src/test/java/com/skryl/edu/dataprovider/DataProviderTests.java).
Also, our provider consists of a set of data, and every data value will be run in a separate test.

## TestNG Listeners

[doc](https://testng.org/doc/documentation-main.html#testng-listeners)

### ITestListener

[ITestListener](https://javadoc.io/doc/org.testng/testng/6.13/org/testng/ITestListener.html) can be helpful in gathering
test info for some custom reports or collecting test execution statistics to [Elasticsearch](https://www.elastic.co/)
for further analysis in [Grafana](https://grafana.com/). Example of Listener
in  [TestInfo.java](src/test/java/com/skryl/edu/listeners/TestInfo.java) and how to apply this listener shown
in [log-test-info-suite.xml](src/test/resources/listener/log-test-info-suite.xml)

### Preconditions

Listeners could be used as preconditions. Sometimes different suits have the same preconditions, sometimes no, and
sometimes mixed preconditions. By implementing ITestListener we can write different preconditions and combine them in
the suite. For instance [Precontion1](src/test/java/com/skryl/edu/listeners/BeforeListener1.java)
and [Precondition2](src/test/java/com/skryl/edu/listeners/BeforeListener1.java) that can both used
in [suite](src/test/java/com/skryl/edu/suits/PreconditionSequenceTest.java) as an annotation or as a listener in xml.

Use ITestContext to provide prepared in advance some object that will be used in the test. For
example [precondition listener which prepares user instance](src/test/java/com/skryl/edu/listeners/PreSetupUser.java)
was added to [test case](src/test/java/com/skryl/edu/suits/PreconditionSequenceTest.java) as a listener and receive user instance in test
method _useContextTest_ as a parameter .

```java

@Slf4j
@Listeners({com.skryl.edu.listeners.PreSetupUser.class})
public class PreconditionSequenceTest {

    @Test
    void useContextTest(ITestContext context) {
        var user = (User) context.getAttribute("testUser");
        log.info("User name: %s".formatted(user.getName()));
        log.info("User age: %d".formatted(user.getAge()));
    }
}
```

### Run TestNG suites with maven

```bash
mvn clean test -pl testng-tests -amd -Dsurefire.suiteXmlFiles=src/test/resources/all-tests.xml
```

