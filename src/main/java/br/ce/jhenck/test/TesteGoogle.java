package br.ce.jhenck.test;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import br.ce.jhenck.core.RemoteBaseTest;

import java.net.MalformedURLException;

public class TesteGoogle extends RemoteBaseTest {
	
	private WebDriver driver;

	@Before
	public void inicializa() throws MalformedURLException{
		driver = newRemoteDriver(new ChromeOptions());
		driver.manage().window().setSize(new Dimension(1200, 765));
	}
	
	@After
	public void finaliza(){
		if(driver != null){
			driver.quit();
		}
	}
	
	@Test
	public void teste() {
		driver.get("http://www.google.com");
		Assert.assertEquals("Google", driver.getTitle());
	}

}
