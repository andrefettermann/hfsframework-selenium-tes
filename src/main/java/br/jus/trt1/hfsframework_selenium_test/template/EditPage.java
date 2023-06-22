/*
 * EditTemplatePage.java
 */
package br.jus.trt1.hfsframework_selenium_test.template;

import org.openqa.selenium.WebDriver;

/**
 * Template de pagina de edicao de dados.
 * @author andre.fettermann - TRT1/STI/CSIS/DISAD
 */
public abstract class EditPage extends BasePage {

	public EditPage(WebDriver driver) {
		super(driver);
	}   
	
	public abstract void grava();
	
	public abstract void cancela();
	
}
