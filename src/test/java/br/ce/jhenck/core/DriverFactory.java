package br.ce.jhenck.core;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
	
	private static WebDriver driver;

	static {
		// Ensures the single browser is closed when the JVM ends (mvn test / IDE),
		// since test classes no longer call killDriver per test.
		Runtime.getRuntime().addShutdownHook(new Thread(() -> killDriver()));
	}
	
	private DriverFactory() {}
	
	public static WebDriver getDriver(){
		if(driver == null) {
			// Selenium 4.6+: Selenium Manager resolves the driver automatically (no manual System.setProperty).
			// Same signature kept to avoid breaking DSL/BaseTest.
			// Headless by default (fast, works on display-less CI).
			// To watch the browser: mvn test -Dheadless=false (or -Dheadless=false VM option in the IDE).
			boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
			switch (Properties.browser) {
				case FIREFOX: {
					FirefoxOptions options = new FirefoxOptions();
					if(headless) {
						options.addArguments("-headless");
					}
					driver = new FirefoxDriver(options); break;
				}
				case CHROME:
				default: {
					ChromeOptions options = new ChromeOptions();
					// Stability on CI/containers (no side effect on local GUI runs).
					options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
					if(headless) {
						options.addArguments("--headless=new");
					}
					driver = new ChromeDriver(options); break;
				}
			}
			driver.manage().window().setSize(new Dimension(1200, 765));			
		}
		return driver;
	}

	/**
	 * Launches Chrome a single time (singleton reused by every test)
	 * and opens the page with a refresh. Call once per class (@BeforeClass).
	 */
	public static void openPage(String url) {
		getDriver().get(url);
		getDriver().navigate().refresh();
	}

	/**
	 * Just reloads the current page (fast, no get). Call before each test (@Before).
	 * Backs to the main content before refreshing to avoid inheriting another test's frame.
	 */
	public static void reloadPage() {
		try {
			getDriver().switchTo().defaultContent();
		} catch (Exception ignored) {
			// E.g. open alert — the test itself handles it; proceed to refresh.
		}
		getDriver().navigate().refresh();
	}

	public static void killDriver(){
		if(driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
