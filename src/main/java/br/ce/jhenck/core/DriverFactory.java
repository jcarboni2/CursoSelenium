package br.ce.jhenck.core;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
	
	private static WebDriver driver;
	
	private DriverFactory() {}
	
	public static WebDriver getDriver(){
		if(driver == null) {
			// Selenium 4.6+: Selenium Manager resolve o driver automaticamente (sem System.setProperty manual).
			// Mantida a mesma assinatura para não quebrar DSL/BaseTest.
			boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
			switch (Propriedades.browser) {
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

	public static void killDriver(){
		if(driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
