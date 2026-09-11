package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.abrirPagina;
import static br.ce.jhenck.core.DriverFactory.recarregarPagina;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;

import br.ce.jhenck.core.BaseTest;
import br.ce.jhenck.core.DSL;

public class PrimeTest extends BaseTest {
	
	private DSL dsl;

	@BeforeClass
	public static void carregarPagina(){
		abrirPagina("file:///" + System.getProperty("user.dir") + "/src/test/resources/prime-local.html");
	}

	@Before
	public void inicializa(){
		recarregarPagina();
		dsl = new DSL();
	}

	@Test
	public void deveInteragirComRadioPrime(){
		dsl.clicarRadio(By.xpath("//input[@id='j_idt701:console:0']/../..//span"));
		Assert.assertTrue(dsl.isRadioMarcado("j_idt701:console:0"));
		dsl.clicarRadio(By.xpath("//label[.='PS4']/..//span"));
		Assert.assertTrue(dsl.isRadioMarcado("j_idt701:console:1"));
	}
	
	@Test
	public void deveInteragirComSelectPrime(){
		dsl.selecionarComboPrime("j_idt701:console", "Xbox One");
		Assert.assertEquals("Xbox One", dsl.obterTexto("j_idt701:console_label"));
	}
}
