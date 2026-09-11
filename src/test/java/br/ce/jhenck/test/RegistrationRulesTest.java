package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.openPage;
import static br.ce.jhenck.core.DriverFactory.reloadPage;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ce.jhenck.core.BaseTest;
import br.ce.jhenck.core.DSL;
import br.ce.jhenck.page.TrainingPage;

@RunWith(Parameterized.class)
public class RegistrationRulesTest extends BaseTest {

	private DSL dsl;
	private TrainingPage page;
	
	@Parameter
	public String firstName;
	@Parameter(value=1)
	public String lastName;
	@Parameter(value=2)
	public String gender;
	@Parameter(value=3)
	public List<String> foods;
	@Parameter(value=4)
	public String[] sports;
	@Parameter(value=5)
	public String expectedMessage;
	

	@BeforeClass
	public static void loadPage(){
		openPage("file:///" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");
	}

	@Before
	public void setUp(){
		reloadPage();
		dsl = new DSL();
		page = new TrainingPage();
	}
	
	@Parameters
	public static Collection<Object[]> getCollection(){
		return Arrays.asList(new Object[][] {
			{"", "", "", Arrays.asList(), new String[]{}, "First name is required"},
			{"James", "", "", Arrays.asList(), new String[]{}, "Last name is required"},
			{"James", "Smith", "", Arrays.asList(), new String[]{}, "Gender is required"},
			{"James", "Smith", "Male", Arrays.asList("Meat", "Vegetarian"), new String[]{}, "Are you sure you are vegetarian?"},
			{"James", "Smith", "Male", Arrays.asList("Meat"), new String[]{"Karate", "What is a sport?"}, "Do you play sports or not?"}
		});
	}
	
	@Test
	public void shouldValidateRules(){
		page.setFirstName(firstName);
		page.setLastName(lastName);
		if(gender.equals("Male")) {
			page.selectMaleGender();
		} 
		if(gender.equals("Female")) {
			page.selectFemaleGender();
		}
		if(foods.contains("Meat")) page.selectMeatOption(); 
		if(foods.contains("Pizza")) page.selectPizzaOption(); 
		if(foods.contains("Vegetarian")) page.selectVegetarianOption(); 
		page.selectSports(sports);
		page.submitRegistration();
		System.out.println(expectedMessage);
		Assert.assertEquals(expectedMessage, dsl.getAlertTextAndAccept());
	}
}
