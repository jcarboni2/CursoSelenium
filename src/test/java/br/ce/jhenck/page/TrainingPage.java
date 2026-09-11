package br.ce.jhenck.page;
import org.openqa.selenium.By;

import br.ce.jhenck.core.BasePage;

public class TrainingPage extends BasePage {

	public void setFirstName(String firstName) {
		dsl.write("elementosForm:nome", firstName);
	}
	
	public void setLastName(String lastName) {
		dsl.write("elementosForm:sobrenome", lastName);
	}
	
	public void selectMaleGender(){
		dsl.clickRadio("elementosForm:sexo:0");
	}
	
	public void selectFemaleGender(){
		dsl.clickRadio("elementosForm:sexo:1");
	}
	
	public void selectMeatOption(){
		dsl.clickRadio("elementosForm:comidaFavorita:0");
	}
	
	public void selectPizzaOption(){
		dsl.clickRadio("elementosForm:comidaFavorita:2");
	}
	
	public void selectVegetarianOption(){
		dsl.clickRadio("elementosForm:comidaFavorita:3");
	}
	
	public void selectEducation(String value) {
		dsl.selectDropdown("elementosForm:escolaridade", value);
	}
	
	public void selectSports(String... values) {
		for(String value: values)
			dsl.selectDropdown("elementosForm:esportes", value);
	}
	
	public void submitRegistration(){
		dsl.clickButton("elementosForm:cadastrar");
	}
	
	public String getRegistrationResult(){
		return dsl.getText(By.xpath("//*[@id='resultado']/span"));
	}
	
	
	public String getRegisteredFirstName(){
		return dsl.getText(By.xpath("//*[@id='descNome']/span"));
	}
	
	public String getRegisteredLastName(){
		return dsl.getText(By.xpath("//*[@id='descSobrenome']/span"));
	}
	
	public String getRegisteredGender(){
		return dsl.getText(By.xpath("//*[@id='descSexo']/span"));
	}
	
	public String getRegisteredFood(){
		return dsl.getText(By.xpath("//*[@id='descComida']/span"));
	}
	
	public String getRegisteredEducation(){
		return dsl.getText(By.xpath("//*[@id='descEscolaridade']/span"));
	}
	
	public String getRegisteredSports(){
		return dsl.getText(By.xpath("//*[@id='descEsportes']/span"));
	}
}
