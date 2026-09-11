package br.ce.jhenck.suites;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.jhenck.core.DriverFactory;
import br.ce.jhenck.test.RegistrationTest;
import br.ce.jhenck.test.RegistrationRulesTest;

@RunWith(Suite.class)
@SuiteClasses({
	RegistrationTest.class,
	RegistrationRulesTest.class
})
public class SuiteTest {
	
	// Closes the single browser at the end of the suite (in mvn test the shutdown hook closes it).
	@AfterClass
	public static void tearDownAll(){
		DriverFactory.killDriver();
	}

}
