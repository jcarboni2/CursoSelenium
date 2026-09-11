package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.abrirPagina;
import static br.ce.jhenck.core.DriverFactory.getDriver;
import static br.ce.jhenck.core.DriverFactory.recarregarPagina;

import java.time.Duration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.ce.jhenck.core.BaseTest;
import br.ce.jhenck.core.DSL;

public class SincronismoTest extends BaseTest {

	private DSL dsl;

	@BeforeClass
	public static void carregarPagina(){
		abrirPagina("file:///" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");
	}

	@Before
	public void inicializa(){
		recarregarPagina();
		dsl = new DSL();
	}
	
	@Test
	public void deveUtilizarEsperaFixa() throws InterruptedException{
		dsl.clicarBotao("buttonDelay");
		Thread.sleep(5000);
		dsl.escrever("novoCampo", "Deu certo?");
	}
	
	@Test
	public void deveUtilizarEsperaImplicita() throws InterruptedException{
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		dsl.clicarBotao("buttonDelay");
		dsl.escrever("novoCampo", "Deu certo?");
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
	}
	

	@Test
	public void deveUtilizarEsperaExplicita() throws InterruptedException{
		dsl.clicarBotao("buttonDelay");
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id("novoCampo")));
		dsl.escrever("novoCampo", "Deu certo?");
	}
}
