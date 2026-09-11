package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.getDriver;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import br.ce.jhenck.core.DSL;
import br.ce.jhenck.core.DriverFactory;

public class TesteAjax {
	
	private DSL dsl;

	@Before
	public void inicializa(){
		getDriver().get("file:///" + System.getProperty("user.dir") + "/src/main/resources/ajax.html");
		dsl = new DSL();
	}
	
	@After
	public void finaliza(){
		DriverFactory.killDriver();
	}

	@Test
	public void testAjax(){
		dsl.escrever("ajax:name", "Teste");
		dsl.clicarBotao("ajax:button");
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ajax:status")));
		Assert.assertEquals("Teste", dsl.obterTexto("ajax:display"));
	}
}
