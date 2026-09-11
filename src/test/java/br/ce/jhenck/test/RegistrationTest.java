package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.openPage;
import static br.ce.jhenck.core.DriverFactory.reloadPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.ce.jhenck.core.BaseTest;
import br.ce.jhenck.page.TrainingPage;

public class RegistrationTest extends BaseTest {
	
	private TrainingPage page;

	@BeforeClass
	public static void loadPage(){
		openPage("file:///" + System.getProperty("user.dir") + "/src/test/resources/componentes.html");
	}

	@Before
	public void setUp(){
		reloadPage();
		page = new TrainingPage();
	}

	@Test
	public void shouldRegisterSuccessfully(){
		page.setFirstName("James");
		page.setLastName("Smith");
		page.selectMaleGender();
		page.selectPizzaOption();
		page.selectEducation("Master's degree");
		page.selectSports("Swimming");
		page.submitRegistration();
		
		Assert.assertEquals("Registered!", page.getRegistrationResult());
		Assert.assertEquals("James", page.getRegisteredFirstName());
		Assert.assertEquals("Smith", page.getRegisteredLastName());
		Assert.assertEquals("Male", page.getRegisteredGender());
		Assert.assertEquals("Pizza", page.getRegisteredFood());
		Assert.assertEquals("masters", page.getRegisteredEducation());
		Assert.assertEquals("Swimming", page.getRegisteredSports());
	}
}
