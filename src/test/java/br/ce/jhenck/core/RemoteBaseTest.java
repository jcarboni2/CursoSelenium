package br.ce.jhenck.core;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

// Infraestrutura de execução remota (não é teste).
// Ordem de resolução do driver (container é opt-in: sem ele, zero contato com Docker):
// 1) Grid externo via -Dselenium.grid.url (sem container);
// 2) Grid em container via -Dselenium.grid.container=true (exige Docker daemon);
// 3) Driver local via Selenium Manager (padrão silencioso).
public class RemoteBaseTest {

	private static GenericContainer<?> grid;

	public static boolean isContainerAtivo() {
		if (Boolean.parseBoolean(System.getProperty("selenium.local", "false"))) {
			return false; // compat: modo local legado
		}
		return Boolean.parseBoolean(System.getProperty("selenium.grid.container", "false"));
	}

	@BeforeClass
	public static void startGrid() {
		if (!System.getProperty("selenium.grid.url", "").trim().isEmpty()) {
			return; // Grid externo: sem container
		}
		if (!isContainerAtivo()) {
			System.out.println("INFO [RemoteBaseTest]: driver local (container desativado por padrao; ative com -Dselenium.grid.container=true).");
			return; // Padrão: local direto, sem sondar Docker, sem ruído no log
		}
		try {
			grid = new GenericContainer<>("selenium/standalone-chrome:latest")
					.withExposedPorts(4444)
					.withCreateContainerCmdModifier(cmd -> cmd.getHostConfig().withShmSize(2147483648L))
					.waitingFor(Wait.forHttp("/wd/hub/status").forStatusCode(200));
			grid.start();
		} catch (RuntimeException e) {
			grid = null;
			System.out.println("AVISO [RemoteBaseTest]: Docker indisponível, usando driver local. Detalhe: " + e.getMessage());
		}
	}

	@AfterClass
	public static void stopGrid() {
		if (grid != null) {
			grid.stop();
			grid = null;
		}
	}

	protected static String gridUrl() {
		String external = System.getProperty("selenium.grid.url", "").trim();
		if (!external.isEmpty()) {
			return external;
		}
		if (grid != null && grid.isRunning()) {
			return "http://" + grid.getHost() + ":" + grid.getMappedPort(4444) + "/wd/hub";
		}
		return null; // sem Grid: fallback local
	}

	protected static WebDriver newRemoteDriver(Capabilities options) throws MalformedURLException {
		String url = gridUrl();
		if (url != null) {
			return new RemoteWebDriver(new URL(url), options);
		}
		String browser = options == null ? "" : String.valueOf(options.getBrowserName());
		if (browser.contains("firefox")) {
			return options instanceof FirefoxOptions ? new FirefoxDriver((FirefoxOptions) options) : new FirefoxDriver();
		}
		return options instanceof ChromeOptions ? new ChromeDriver((ChromeOptions) options) : new ChromeDriver();
	}
}
