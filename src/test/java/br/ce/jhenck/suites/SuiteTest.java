package br.ce.jhenck.suites;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.jhenck.core.DriverFactory;
import br.ce.jhenck.test.CadastroTest;
import br.ce.jhenck.test.RegrasCadastroTest;

@RunWith(Suite.class)
@SuiteClasses({
	CadastroTest.class,
	RegrasCadastroTest.class
})
public class SuiteTest {
	
	// Fecha o browser único ao fim da suíte (no mvn test o fechamento é via shutdown hook).
	@AfterClass
	public static void finalizaTudo(){
		DriverFactory.killDriver();
	}

}
