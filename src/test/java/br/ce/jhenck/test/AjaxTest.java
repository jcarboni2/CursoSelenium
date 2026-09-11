package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.openPage;
import static br.ce.jhenck.core.DriverFactory.getDriver;
import static br.ce.jhenck.core.DriverFactory.reloadPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import br.ce.jhenck.core.BaseTest;
import br.ce.jhenck.core.DSL;

public class AjaxTest extends BaseTest {
	
	private DSL dsl;

	@BeforeClass
	public static void loadPage(){
		openPage("file:///" + System.getProperty("user.dir") + "/src/test/resources/ajax.html");
	}

	@Before
	public void setUp(){
		reloadPage();
		dsl = new DSL();
	}

	@Test
	public void shouldSubmitViaAjax(){
		dsl.write("ajax:name", "Test");
		dsl.clickButton("ajax:button");
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ajax:status")));
		Assert.assertEquals("Test", dsl.getText("ajax:display"));
	}
}
