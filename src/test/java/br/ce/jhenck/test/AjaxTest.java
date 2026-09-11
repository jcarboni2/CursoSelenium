package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.abrirPagina;
import static br.ce.jhenck.core.DriverFactory.getDriver;
import static br.ce.jhenck.core.DriverFactory.recarregarPagina;

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
	public static void carregarPagina(){
		abrirPagina("file:///" + System.getProperty("user.dir") + "/src/test/resources/ajax.html");
	}

	@Before
	public void inicializa(){
		recarregarPagina();
		dsl = new DSL();
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
