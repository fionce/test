package tests;

import static org.junit.Assert.*;

import models.ErrorsReporter;
import models.ExceptionWrapperRule;
import models.TestNameSniffer;
import models.TestParameters;
import models.TestsLogger;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.seleniumhq.jetty7.util.log.Log;

import seleniumObjects.CommonPage;

public class RulesTests {
	private static CommonPage page = CommonPage.getInstance();
	private static TestsLogger testslog = new TestsLogger(RulesTests.class);
	private int run =-1;
	// expected Exception rule
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	// custom rule
	@Rule
	public ExceptionWrapperRule ewr = new ExceptionWrapperRule();

	// custom test name rule
	@Rule
	public TestNameSniffer name = new TestNameSniffer();
	
	@Rule
	public TestName nameDefault = new TestName();

//	@Rule
//	public Timeout ruleTimeout = new Timeout(1000);
	// error collector
	@Rule
	public ErrorCollector collector = new ErrorCollector();

	// external resource
	@ClassRule
	public static ExternalResource resource = new ExternalResource() {
		@Override
		protected void before() throws Throwable {
			testslog.logInfo("ClassRule started");
			page.openBrowser();
		};

		@Override
		protected void after() {
			testslog.logInfo("ClassRule finished");
			page.closeBrowser();
		};
	};

	@Before
	public void setup() {
		run++;
		testslog.logInfo("Before started");
	}

	// test watcher rule
	@Rule
	public TestWatcher watchman = new TestWatcher() {
		@Override
		protected void failed(Throwable e, Description description) {
			testslog.logError("Failed: " + nameDefault.getMethodName() + " "
					+ e.getMessage());
		}

		@Override
		protected void succeeded(Description description) {
			testslog.logInfo("Succeeded: " + name.testName());
		}
	};

	//another custom rule - for data driven
	@Rule
	public TestParameters params = new TestParameters();
	
	
	@Test
	public void testA() throws InterruptedException {
		// expected exception
		//thrown.expect(ErrorsReporter.class);

		// exception message expected
		//thrown.expectMessage("Element not found in the cache - perhaps the page has changed since it was looked up");

		// test steps
		page.loadPage("http://junit.org/");
		WebElement carouselNext = page.getDriver().findElement(
				By.id("main-carousel-next"));
		page.loadPage("https://github.com/junit-team/junit/wiki/Rules");
		//carouselNext.click();
	}

	@Test
	public void testB() throws ErrorsReporter {
		// expected exception
		thrown.expect(ErrorsReporter.class);

		// test step
		page.loadPage("http://junit.org/");
		// trigger exception
		try {
			assertEquals(1, 2);
		} catch (Throwable t) {
			collector.addError(t);
			collector.checkThat(1, CoreMatchers.equalTo(2));
			throw new ErrorsReporter(t.getLocalizedMessage());
		}
		WebElement carouselNext = page.getDriver().findElement(
				By.id("main-carousel-next"));
		carouselNext.click();
		page.loadPage("https://github.com/junit-team/junit/wiki/Rules");

		// trigger exception
		try {
			assertEquals("", "not empty");
		} catch (Throwable t) {
			collector.addError(t);
			throw new ErrorsReporter(t.getLocalizedMessage());
		}
	}

	@Test
	public void testParams() throws Exception {
		assertTrue(params.testValues()[run].contains("test"));
		testslog.logInfo(params.testValues()[run]);
	}

}
