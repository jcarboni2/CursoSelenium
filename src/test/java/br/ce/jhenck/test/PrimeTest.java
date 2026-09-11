package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.openPage;
import static br.ce.jhenck.core.DriverFactory.reloadPage;

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
	public static void loadPage(){
		openPage("file:///" + System.getProperty("user.dir") + "/src/test/resources/prime-local.html");
	}

	@Before
	public void setUp(){
		reloadPage();
		dsl = new DSL();
	}

	@Test
	public void shouldInteractWithPrimeRadio(){
		dsl.clickRadio(By.xpath("//input[@id='j_idt701:console:0']/../..//span"));
		Assert.assertTrue(dsl.isRadioSelected("j_idt701:console:0"));
		dsl.clickRadio(By.xpath("//label[.='PS4']/..//span"));
		Assert.assertTrue(dsl.isRadioSelected("j_idt701:console:1"));
	}
	
	@Test
	public void shouldInteractWithPrimeDropdown(){
		dsl.selectPrimeDropdown("j_idt701:console", "Xbox One");
		Assert.assertEquals("Xbox One", dsl.getText("j_idt701:console_label"));
	}
}
