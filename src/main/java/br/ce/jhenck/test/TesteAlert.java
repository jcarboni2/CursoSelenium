package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.abrirPagina;
import static br.ce.jhenck.core.DriverFactory.recarregarPagina;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.ce.jhenck.core.BaseTest;
import br.ce.jhenck.core.DSL;

public class TesteAlert extends BaseTest {
	
	private DSL dsl;
	
	@BeforeClass
	public static void carregarPagina(){
		abrirPagina("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
	}

	@Before
	public void inicializa(){
		recarregarPagina();
		dsl = new DSL();
	}

	@Test
	public void deveInteragirComAlertSimples(){
		dsl.clicarBotao("alert");
		String texto = dsl.alertaObterTextoEAceita(); 
		Assert.assertEquals("Alert Simples", texto);
		
		dsl.escrever("elementosForm:nome", texto);
	}
	
	@Test
	public void deveInteragirComAlertConfirm(){
		dsl.clicarBotao("confirm");
		Assert.assertEquals("Confirm Simples", dsl.alertaObterTextoEAceita());
		Assert.assertEquals("Confirmado", dsl.alertaObterTextoEAceita());
		
		dsl.clicarBotao("confirm");
		Assert.assertEquals("Confirm Simples", dsl.alertaObterTextoENega());
		Assert.assertEquals("Negado", dsl.alertaObterTextoENega());
	}
	
	@Test
	public void deveInteragirComAlertPrompt(){
		dsl.clicarBotao("prompt");
		Assert.assertEquals("Digite um numero", dsl.alertaObterTexto());
		dsl.alertaEscrever("12");
		Assert.assertEquals("Era 12?", dsl.alertaObterTextoEAceita());
		Assert.assertEquals(":D", dsl.alertaObterTextoEAceita());
	}
}
