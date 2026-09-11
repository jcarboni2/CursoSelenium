package br.ce.jhenck.test;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import br.ce.jhenck.core.RemoteBaseTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;

public class GoogleTest extends RemoteBaseTest {
	
	// Browser único da classe: iniciado uma vez (@BeforeClass), página recarregada por teste.
	// Sem internet o teste é pulado (skipped, sem falhar o build) com mensagem evidente.
	private static WebDriver driver;
	private static boolean semConexao = false;

	@BeforeClass
	public static void carregarBrowser() throws MalformedURLException{
		semConexao = !temConexao();
		if (semConexao) {
			System.out.println("AVISO [GoogleTest]: sem conexão com www.google.com — teste NÃO executado (skipped, build segue verde).");
			return;
		}
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
		if (Boolean.parseBoolean(System.getProperty("headless", "true"))) {
			options.addArguments("--headless=new");
		}
		driver = newRemoteDriver(options);
		driver.manage().window().setSize(new Dimension(1200, 765));
		driver.get("http://www.google.com");
	}

	@Before
	public void inicializa(){
		Assume.assumeTrue("Sem conexão com www.google.com — GoogleTest ignorado.", !semConexao);
		driver.navigate().refresh();
	}
	
	@AfterClass
	public static void finaliza(){
		if(driver != null){
			driver.quit();
			driver = null;
		}
	}
	
	@Test
	public void teste() {
		Assert.assertEquals("Google", driver.getTitle());
	}

	// Sonda rápida (3s): evita que a falta de internet quebre o build.
	private static boolean temConexao() {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress("www.google.com", 443), 3000);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

}
