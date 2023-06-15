/*
 * EditTemplatePage.java
 */
package br.jus.trt1.hfsframework_selenium_test.template;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Base page.
 * @author andre.fettermann - TRT1/DISAD
 */
public abstract class EditPage {

	public WebDriver driver;
	
	@FindBy(how = How.ID, using = "messages")
	protected WebElement messages;

	@FindBy(how = How.ID, using = "primefacesmessagedlg")
	private WebElement dialog;

	public EditPage(WebDriver driver) {
		this.driver = driver;
	}
	
    public String getTitle() {
    	return driver.getTitle().replaceAll("\u00A0", " ");
    }
    
	public List<String> getMessages() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(2))
				.until(ExpectedConditions.visibilityOf(messages));
			String[] lines = messages.getText().split("\\r?\\n|\\r");
			return Arrays.asList(lines);
					//mensagens.getText();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public boolean isMessagePresent(String mensagem) {
		return getMessages().contains(mensagem);
	}
	
	public byte[] takeScreenShot() {
		final byte[] screenshot = 
				((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		return screenshot;
	}

	public boolean isAlertPresent(String mensagemDesejada) {
		new WebDriverWait(driver, Duration.ofSeconds(2))
			.until(ExpectedConditions.visibilityOf(dialog));
		
		String mensagemObtida = driver.findElement(
					By.xpath(
						"//div[@id='primefacesmessagedlg']/div[2]")).getText();		
		
		driver.findElement(
				By.xpath("//div[@id='primefacesmessagedlg']/div/a")).click();
		
		if (mensagemDesejada.equals(mensagemObtida)) {
			return true;
		}
		return false;
	}


	public String getElementValue(WebElement element) {
		return element.getAttribute("value");
	}

    public void setElementText(WebElement element, String texto) {
    	if (texto == null) texto = "";
    	element.clear();
    	element.sendKeys(texto);
    }
    
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public abstract void save();
	
	public abstract void cancel();
	
	protected void clickButton(WebElement element) {
		element.click();
	}

}
