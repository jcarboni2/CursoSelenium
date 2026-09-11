package br.ce.jhenck.core;

import static br.ce.jhenck.core.DriverFactory.getDriver;
import static br.ce.jhenck.core.DriverFactory.killDriver;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class BaseTest {
	
	@Rule
	public TestName testName = new TestName();

	@Before
	public void setUpBase() {
		// Ensures the single browser before each test (created once, reused).
		// Teardown is global: SuiteTest.@AfterClass or JVM shutdown hook.
		getDriver();
	}
	
	@After
	public void tearDown() throws IOException{
		TakesScreenshot ss = (TakesScreenshot) getDriver();
		File screenshotFile = ss.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotFile, new File("target" + File.separator + "screenshot" +
				File.separator + testName.getMethodName() + ".jpg"));
		
		if(Properties.CLOSE_BROWSER) {
			killDriver();
		}
	}

}
