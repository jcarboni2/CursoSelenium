package br.ce.jhenck.test;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import br.ce.jhenck.core.RemoteBaseTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;

public class GoogleTest extends RemoteBaseTest {
	
	// Single class-level browser: launched once (@BeforeClass), page reloaded per test.
	// Without internet the test is skipped (no build failure) with a clear message.
	private static WebDriver driver;
	private static boolean offline = false;

	@BeforeClass
	public static void launchBrowser() throws MalformedURLException{
		offline = !hasConnection();
		if (offline) {
			System.out.println("WARNING [GoogleTest]: no connection to www.google.com — test NOT executed (skipped, build stays green).");
			return;
		}
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
		if (Boolean.parseBoolean(System.getProperty("headless", "true"))) {
			options.addArguments("--headless=new");
		}
		driver = newRemoteDriver(options);
		driver.manage().window().setSize(new Dimension(1200, 765));
		driver.get("http://www.google.com");
	}

	@Before
	public void setUp(){
		Assume.assumeTrue("No connection to www.google.com — GoogleTest skipped.", !offline);
		driver.navigate().refresh();
	}
	
	@AfterClass
	public static void tearDown(){
		if(driver != null){
			driver.quit();
			driver = null;
		}
	}
	
	@Test
	public void shouldDisplayGoogleTitle() {
		Assert.assertEquals("Google", driver.getTitle());
	}

	// Quick probe (3s): prevents missing internet from breaking the build.
	private static boolean hasConnection() {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress("www.google.com", 443), 3000);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
