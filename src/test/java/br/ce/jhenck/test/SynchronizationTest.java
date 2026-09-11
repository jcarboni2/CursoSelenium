package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.openPage;
import static br.ce.jhenck.core.DriverFactory.getDriver;
import static br.ce.jhenck.core.DriverFactory.reloadPage;

import java.time.Duration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.ce.jhenck.core.BaseTest;
import br.ce.jhenck.core.DSL;

public class SynchronizationTest extends BaseTest {

	private DSL dsl;

	@BeforeClass
	public static void loadPage(){
		openPage("file:///" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");
	}

	@Before
	public void setUp(){
		reloadPage();
		dsl = new DSL();
	}
	
	@Test
	public void shouldUseFixedWait() throws InterruptedException{
		dsl.clickButton("buttonDelay");
		Thread.sleep(5000);
		dsl.write("novoCampo", "Did it work?");
	}
	
	@Test
	public void shouldUseImplicitWait() throws InterruptedException{
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		dsl.clickButton("buttonDelay");
		dsl.write("novoCampo", "Did it work?");
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
	}
	

	@Test
	public void shouldUseExplicitWait() throws InterruptedException{
		dsl.clickButton("buttonDelay");
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("novoCampo")));
		dsl.write("novoCampo", "Did it work?");
	}
}
