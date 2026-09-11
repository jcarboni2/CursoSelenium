package br.ce.jhenck.core;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {
	
	private static WebDriver driver;

	static {
		// Garante que o browser único seja fechado ao fim da JVM (mvn test / IDE),
		// já que as classes de teste não fazem mais killDriver por teste.
		Runtime.getRuntime().addShutdownHook(new Thread(() -> killDriver()));
	}
	
	private DriverFactory() {}
	
	public static WebDriver getDriver(){
		if(driver == null) {
			// Selenium 4.6+: Selenium Manager resolve o driver automaticamente (sem System.setProperty manual).
			// Mantida a mesma assinatura para não quebrar DSL/BaseTest.
			// Headless por padrão (rápido, funciona em CI sem display).
			// Para ver o browser: mvn test -Dheadless=false (ou VM option -Dheadless=false na IDE).
			boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
			switch (Propriedades.browser) {
				case FIREFOX: {
					FirefoxOptions options = new FirefoxOptions();
					if(headless) {
						options.addArguments("-headless");
					}
					driver = new FirefoxDriver(options); break;
				}
				case CHROME:
				default: {
					ChromeOptions options = new ChromeOptions();
					// Estabilidade em CI/container (sem efeito colateral no uso local com GUI).
					options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
					if(headless) {
						options.addArguments("--headless=new");
					}
					driver = new ChromeDriver(options); break;
				}
			}
			driver.manage().window().setSize(new Dimension(1200, 765));			
		}
		return driver;
	}

	/**
	 * Sobe o Chrome uma única vez (singleton reaproveitado por todos os testes)
	 * e abre a página já com refresh. Chamar uma vez por classe (@BeforeClass).
	 */
	public static void abrirPagina(String url) {
		getDriver().get(url);
		getDriver().navigate().refresh();
	}

	/**
	 * Só recarrega a página atual (rápido, sem get). Chamar antes de cada teste (@Before).
	 * Volta ao conteúdo principal antes do refresh para não herdar frame de outro teste.
	 */
	public static void recarregarPagina() {
		try {
			getDriver().switchTo().defaultContent();
		} catch (Exception ignored) {
			// Ex.: alerta aberto — o próprio teste trata; segue para o refresh.
		}
		getDriver().navigate().refresh();
	}

	public static void killDriver(){
		if(driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
