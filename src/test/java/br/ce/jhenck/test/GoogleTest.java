package br.ce.jhenck.test;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import br.ce.jhenck.core.RemoteBaseTest;

import java.net.MalformedURLException;

public class GoogleTest extends RemoteBaseTest {
	
	// Browser único da classe: iniciado uma vez (@BeforeClass), página recarregada por teste.
	private static WebDriver driver;

	@BeforeClass
	public static void carregarBrowser() throws MalformedURLException{
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
	public void inicializa(){
		driver.navigate().refresh();
	}
	
	@AfterClass
	public static void finaliza(){
		if(driver != null){
			driver.quit();
			driver = null;
		}
	}
	
	@Test
	public void teste() {
		Assert.assertEquals("Google", driver.getTitle());
	}

}
