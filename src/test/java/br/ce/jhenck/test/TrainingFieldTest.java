package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.openPage;
import static br.ce.jhenck.core.DriverFactory.getDriver;
import static br.ce.jhenck.core.DriverFactory.reloadPage;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import br.ce.jhenck.core.BaseTest;
import br.ce.jhenck.core.DSL;

public class TrainingFieldTest extends BaseTest {
	
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
	public void shouldTypeInTextField(){
		dsl.write("elementosForm:nome", "Writing test");
		Assert.assertEquals("Writing test", dsl.getFieldValue("elementosForm:nome"));
	}
	
	@Test
	public void shouldOverwriteTextField(){
		dsl.write("elementosForm:nome", "James");
		Assert.assertEquals("James", dsl.getFieldValue("elementosForm:nome"));
		dsl.write("elementosForm:nome", "Smith");
		Assert.assertEquals("Smith", dsl.getFieldValue("elementosForm:nome"));
	}
	
	@Test
	public void shouldInteractWithTextArea(){
		dsl.write("elementosForm:sugestoes", "test\n\nrandom text\nLast line");
		Assert.assertEquals("test\n\nrandom text\nLast line", dsl.getFieldValue("elementosForm:sugestoes"));
	}
	
	@Test
	public void shouldInteractWithRadioButton(){
		dsl.clickRadio("elementosForm:sexo:0");
		Assert.assertTrue(dsl.isRadioSelected("elementosForm:sexo:0"));
	}
	
	@Test
	public void shouldInteractWithCheckbox(){
		dsl.clickCheckbox("elementosForm:comidaFavorita:2");
		Assert.assertTrue(dsl.isCheckboxSelected("elementosForm:comidaFavorita:2"));
	}
	
	@Test
	public void shouldInteractWithDropdown(){
		dsl.selectDropdown("elementosForm:escolaridade", "Complete high school");
		Assert.assertEquals("Complete high school", dsl.getSelectedDropdownValue("elementosForm:escolaridade"));
	}
	
	@Test
	public void shouldVerifyDropdownValues(){
		Assert.assertEquals(8, dsl.getDropdownOptionCount("elementosForm:escolaridade"));
		Assert.assertTrue(dsl.hasDropdownOption("elementosForm:escolaridade", "Master's degree"));
	}
	
	@Test
	public void shouldVerifyMultiSelectDropdownValues(){
		dsl.selectDropdown("elementosForm:esportes", "Swimming");
		dsl.selectDropdown("elementosForm:esportes", "Running");
		dsl.selectDropdown("elementosForm:esportes", "What is a sport?");

		List<String> selectedOptions = dsl.getSelectedDropdownValues("elementosForm:esportes");
		Assert.assertEquals(3, selectedOptions.size());
		
		dsl.deselectDropdown("elementosForm:esportes", "Running");
		selectedOptions = dsl.getSelectedDropdownValues("elementosForm:esportes");
		Assert.assertEquals(2, selectedOptions.size());
		Assert.assertTrue(selectedOptions.containsAll(Arrays.asList("Swimming", "What is a sport?")));
	}
	
	@Test
	public void shouldInteractWithButtons(){
		dsl.clickButton("buttonSimple");
		Assert.assertEquals("Thank you!", dsl.getElementValue("buttonSimple"));
	}
	
	@Test
	public void shouldInteractWithLinks(){
		dsl.clickLink("Back");
		
		Assert.assertEquals("Back!", dsl.getText("resultado"));
	}
	
	@Test
	public void shouldFindTextsOnPage(){
//		Assert.assertTrue(driver.findElement(By.tagName("body"))
//				.getText().contains("Training Field"));
		Assert.assertEquals("Training Field", dsl.getText(By.tagName("h3")));
		
		Assert.assertEquals("Careful where you click, many pitfalls...", 
				dsl.getText(By.className("facilAchar")));
	}
	
	@Test
	public void shouldExecuteJavascript(){
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
//		js.executeScript("alert('Testing js via selenium')");
		js.executeScript("document.getElementById('elementosForm:nome').value = 'Written via js'");
		js.executeScript("document.getElementById('elementosForm:sobrenome').type = 'radio'");
		
		WebElement element = getDriver().findElement(By.id("elementosForm:nome"));
		js.executeScript("arguments[0].style.border = arguments[1]", element, "solid 4px red");
	}
	
	@Test
	public void shouldClickTableButton(){
		dsl.clickTableButton("Education", "Master's degree", "Radio", "elementosForm:tableUsuarios");
	}
	
}

