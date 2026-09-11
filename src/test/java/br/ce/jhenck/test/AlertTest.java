package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.openPage;
import static br.ce.jhenck.core.DriverFactory.reloadPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.ce.jhenck.core.BaseTest;
import br.ce.jhenck.core.DSL;

public class AlertTest extends BaseTest {
	
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
	public void shouldInteractWithSimpleAlert(){
		dsl.clickButton("alert");
		String text = dsl.getAlertTextAndAccept(); 
		Assert.assertEquals("Simple Alert", text);
		
		dsl.write("elementosForm:nome", text);
	}
	
	@Test
	public void shouldInteractWithConfirmAlert(){
		dsl.clickButton("confirm");
		Assert.assertEquals("Simple Confirm", dsl.getAlertTextAndAccept());
		Assert.assertEquals("Confirmed", dsl.getAlertTextAndAccept());
		
		dsl.clickButton("confirm");
		Assert.assertEquals("Simple Confirm", dsl.getAlertTextAndDismiss());
		Assert.assertEquals("Denied", dsl.getAlertTextAndDismiss());
	}
	
	@Test
	public void shouldInteractWithPromptAlert(){
		dsl.clickButton("prompt");
		Assert.assertEquals("Enter a number", dsl.getAlertText());
		dsl.writeToAlert("12");
		Assert.assertEquals("Was it 12?", dsl.getAlertTextAndAccept());
		Assert.assertEquals(":D", dsl.getAlertTextAndAccept());
	}
}
