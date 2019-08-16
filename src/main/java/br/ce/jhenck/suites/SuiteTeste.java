package br.ce.jhenck.suites;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.jhenck.core.DriverFactory;
import br.ce.jhenck.test.TesteCadastro;
import br.ce.jhenck.test.TesteRegrasCadastro;

@RunWith(Suite.class)
@SuiteClasses({
	TesteCadastro.class,
	TesteRegrasCadastro.class
})
public class SuiteTeste {
	
	@AfterClass
	public static void finalizaTudo(){
		DriverFactory.killDriver();
	}

}
