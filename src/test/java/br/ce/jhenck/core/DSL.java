package br.ce.jhenck.core;
import static br.ce.jhenck.core.DriverFactory.getDriver;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DSL {
	
	/********* TextField and TextArea ************/
	
	public void write(By locator, String text){
		getDriver().findElement(locator).clear();
		getDriver().findElement(locator).sendKeys(text);
	}

	public void write(String fieldId, String text){
		write(By.id(fieldId), text);
	}
	
	public String getFieldValue(String fieldId) {
		return getDriver().findElement(By.id(fieldId)).getAttribute("value");
	}
	
	/********* Radio and Checkbox ************/
	
	public void clickRadio(By locator) {
		getDriver().findElement(locator).click();
	}
	
	public void clickRadio(String id) {
		clickRadio(By.id(id));
	}
	
	public boolean isRadioSelected(String id){
		return getDriver().findElement(By.id(id)).isSelected();
	}
	
	public void clickCheckbox(String id) {
		getDriver().findElement(By.id(id)).click();
	}
	
	public boolean isCheckboxSelected(String id){
		return getDriver().findElement(By.id(id)).isSelected();
	}
	
	/********* Dropdown ************/
	
	public void selectDropdown(String id, String value) {
		WebElement element = getDriver().findElement(By.id(id));
		Select dropdown = new Select(element);
		dropdown.selectByVisibleText(value);
	}
	
	public void deselectDropdown(String id, String value) {
		WebElement element = getDriver().findElement(By.id(id));
		Select dropdown = new Select(element);
		dropdown.deselectByVisibleText(value);
	}

	public String getSelectedDropdownValue(String id) {
		WebElement element = getDriver().findElement(By.id(id));
		Select dropdown = new Select(element);
		return dropdown.getFirstSelectedOption().getText();
	}
	
	public List<String> getSelectedDropdownValues(String id) {
		WebElement element = getDriver().findElement(By.id("elementosForm:esportes"));
		Select dropdown = new Select(element);
		List<WebElement> allSelectedOptions = dropdown.getAllSelectedOptions();
		List<String> values = new ArrayList<String>();
		for(WebElement option: allSelectedOptions) {
			values.add(option.getText());
		}
		return values;
	}
	
	public int getDropdownOptionCount(String id){
		WebElement element = getDriver().findElement(By.id(id));
		Select dropdown = new Select(element);
		List<WebElement> options = dropdown.getOptions();
		return options.size();
	}
	
	public boolean hasDropdownOption(String id, String expectedOption){
		WebElement element = getDriver().findElement(By.id(id));
		Select dropdown = new Select(element);
		List<WebElement> options = dropdown.getOptions();
		for(WebElement option: options) {
			if(option.getText().equals(expectedOption)){
				return true;
			}
		}
		return false;
	}
	
	public void selectPrimeDropdown(String baseId, String value) {
		clickRadio(By.xpath("//*[@id='"+baseId+"_input']/../..//span"));
		clickRadio(By.xpath("//*[@id='"+baseId+"_items']//li[.='"+value+"']"));
	}
	
	/********* Button ************/
	
	public void clickButton(String id) {
		getDriver().findElement(By.id(id)).click();
	}
	
	public String getElementValue(String id) {
		return getDriver().findElement(By.id(id)).getAttribute("value");
	}
	
	/********* Link ************/
	
	public void clickLink(String linkText) {
		getDriver().findElement(By.linkText(linkText)).click();
	}
	
	/********* Texts ************/
	
	public String getText(By locator) {
		return getDriver().findElement(locator).getText();
	}
	
	public String getText(String id) {
		return getText(By.id(id));
	}
	
	/********* Alerts ************/
	
	public String getAlertText(){
		Alert alert = getDriver().switchTo().alert();
		return alert.getText();
	}
	
	public String getAlertTextAndAccept(){
		Alert alert = getDriver().switchTo().alert();
		String value = alert.getText();
		alert.accept();
		return value;
		
	}
	
	public String getAlertTextAndDismiss(){
		Alert alert = getDriver().switchTo().alert();
		String value = alert.getText();
		alert.dismiss();
		return value;
		
	}
	
	public void writeToAlert(String value) {
		Alert alert = getDriver().switchTo().alert();
		alert.sendKeys(value);
		alert.accept();
	}
	
	/********* Frames and Windows ************/
	
	public void switchToFrame(String id) {
		getDriver().switchTo().frame(id);
	}
	
	public void exitFrame(){
		getDriver().switchTo().defaultContent();
	}
	
	public void switchToWindow(String id) {
		getDriver().switchTo().window(id);
	}
	
	/************** JS *********************/
	
	public Object executeJS(String cmd, Object... param) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		return js.executeScript(cmd, param);
	}
	
	/************** Table *********************/
	
	public void clickTableButton(String searchColumn, String value, String buttonColumn, String tableId){
		//find the record column
		WebElement table = getDriver().findElement(By.xpath("//*[@id='elementosForm:tableUsuarios']"));
		int columnIndex = getColumnIndex(searchColumn, table);
		
		//find the record row
		int rowIndex = getRowIndex(value, table, columnIndex);
		
		//find the button column
		int buttonColumnIndex = getColumnIndex(buttonColumn, table);
		
		//click the button in the found cell
		WebElement cell = table.findElement(By.xpath(".//tr["+rowIndex+"]/td["+buttonColumnIndex+"]"));
		cell.findElement(By.xpath(".//input")).click();
		
	}

	protected int getRowIndex(String value, WebElement table, int columnIndex) {
		List<WebElement> rows = table.findElements(By.xpath("./tbody/tr/td["+columnIndex+"]"));
		int rowIndex = -1;
		for(int i = 0; i < rows.size(); i++) {
			if(rows.get(i).getText().equals(value)) {
				rowIndex = i+1;
				break;
			}
		}
		return rowIndex;
	}

	protected int getColumnIndex(String column, WebElement table) {
		List<WebElement> columns = table.findElements(By.xpath(".//th"));
		int columnIndex = -1;
		for(int i = 0; i < columns.size(); i++) {
			if(columns.get(i).getText().equals(column)) {
				columnIndex = i+1;
				break;
			}
		}
		return columnIndex;
	}
}
