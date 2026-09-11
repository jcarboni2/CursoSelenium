package br.ce.jhenck.test;
import static br.ce.jhenck.core.DriverFactory.openPage;
import static br.ce.jhenck.core.DriverFactory.getDriver;
import static br.ce.jhenck.core.DriverFactory.reloadPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.ce.jhenck.core.BaseTest;
import br.ce.jhenck.core.DSL;


public class FramesAndWindowsTest extends BaseTest {
	
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
	public void shouldInteractWithFrames(){
		dsl.switchToFrame("frame1");
		dsl.clickButton("frameButton");
		String message = dsl.getAlertTextAndAccept();
		Assert.assertEquals("Frame OK!", message);

		dsl.exitFrame();
		dsl.write("elementosForm:nome", message);
	}
	
	@Test
	public void shouldInteractWithHiddenFrame(){
		WebElement frame = getDriver().findElement(By.id("frame2"));
		dsl.executeJS("window.scrollBy(0, arguments[0])", frame.getLocation().y);
		dsl.switchToFrame("frame2");
		dsl.clickButton("frameButton");
		String message = dsl.getAlertTextAndAccept();
		Assert.assertEquals("Frame OK!", message);
	}
	
	@Test
	public void shouldInteractWithWindows(){
		dsl.clickButton("buttonPopUpEasy");
		dsl.switchToWindow("Popup");
		dsl.write(By.tagName("textarea"), "Did it work?");
		getDriver().close();
		dsl.switchToWindow("");
		dsl.write(By.tagName("textarea"), "and now?");
	}
	
	@Test
	public void shouldInteractWithUntitledWindows(){
		dsl.clickButton("buttonPopUpHard");
		System.out.println(getDriver().getWindowHandle());
		System.out.println(getDriver().getWindowHandles());
		dsl.switchToWindow((String) getDriver().getWindowHandles().toArray()[1]);
		dsl.write(By.tagName("textarea"), "Did it work?");
		dsl.switchToWindow((String) getDriver().getWindowHandles().toArray()[0]);
		dsl.write(By.tagName("textarea"), "and now?");
	}
}
