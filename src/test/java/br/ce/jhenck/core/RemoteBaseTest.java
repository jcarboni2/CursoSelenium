package br.ce.jhenck.core;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

// Remote execution infrastructure (not a test).
// Driver resolution order (container is opt-in: without it, zero Docker contact):
// 1) External Grid via -Dselenium.grid.url (no container);
// 2) Containerized Grid via -Dselenium.grid.container=true (requires Docker daemon);
// 3) Local driver via Selenium Manager (silent default).
public class RemoteBaseTest {

	private static GenericContainer<?> grid;

	public static boolean isContainerEnabled() {
		if (Boolean.parseBoolean(System.getProperty("selenium.local", "false"))) {
			return false; // compat: legacy local mode
		}
		return Boolean.parseBoolean(System.getProperty("selenium.grid.container", "false"));
	}

	@BeforeClass
	public static void startGrid() {
		if (!System.getProperty("selenium.grid.url", "").trim().isEmpty()) {
			return; // External Grid: no container
		}
		if (!isContainerEnabled()) {
			System.out.println("INFO [RemoteBaseTest]: local driver (container disabled by default; enable with -Dselenium.grid.container=true).");
			return; // Default: direct local, no Docker probing, no log noise
		}
		try {
			grid = new GenericContainer<>("selenium/standalone-chrome:latest")
					.withExposedPorts(4444)
					.withCreateContainerCmdModifier(cmd -> cmd.getHostConfig().withShmSize(2147483648L))
					.waitingFor(Wait.forHttp("/wd/hub/status").forStatusCode(200));
			grid.start();
		} catch (RuntimeException e) {
			grid = null;
			System.out.println("WARNING [RemoteBaseTest]: Docker unavailable, using local driver. Detail: " + e.getMessage());
		}
	}

	@AfterClass
	public static void stopGrid() {
		if (grid != null) {
			grid.stop();
			grid = null;
		}
	}

	protected static String gridUrl() {
		String external = System.getProperty("selenium.grid.url", "").trim();
		if (!external.isEmpty()) {
			return external;
		}
		if (grid != null && grid.isRunning()) {
			return "http://" + grid.getHost() + ":" + grid.getMappedPort(4444) + "/wd/hub";
		}
		return null; // no Grid: local fallback
	}

	protected static WebDriver newRemoteDriver(Capabilities options) throws MalformedURLException {
		String url = gridUrl();
		if (url != null) {
			return new RemoteWebDriver(new URL(url), options);
		}
		String browser = options == null ? "" : String.valueOf(options.getBrowserName());
		if (browser.contains("firefox")) {
			return options instanceof FirefoxOptions ? new FirefoxDriver((FirefoxOptions) options) : new FirefoxDriver();
		}
		return options instanceof ChromeOptions ? new ChromeDriver((ChromeOptions) options) : new ChromeDriver();
	}
}
