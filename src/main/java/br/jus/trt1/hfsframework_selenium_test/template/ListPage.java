/*
 * ListPage.java
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

import br.jus.trt1.hfsframework_selenium_test.utils.Selenium;

/**
 * List page template.
 * @author andre.fettermann - TRT1/DISAD
 */
public abstract class ListPage {

	public static final String NENHUM_REGISTRO_ENCONTRADO = 
			"Nenhum registro encontrado.";

	public WebDriver driver;
	
	@FindBy(how = How.ID, using = "messages")
	protected WebElement messages;

	@FindBy(how = How.ID, using = "primefacesmessagedlg")
	private WebElement dialog;

	public ListPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public abstract void open();
	
	public abstract void open(String url);

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
	
	public String getDialogMessage() {
		new WebDriverWait(driver, Duration.ofSeconds(2))
		.until(ExpectedConditions.visibilityOf(dialog));
	
		String message = driver.findElement(
				By.xpath(
					"//div[@id='primefacesmessagedlg']/div[2]")).getText();		
		driver.findElement(
				By.xpath("//div[@id='primefacesmessagedlg']/div/a")).click();
		return message;
	}

	public String getElementValue(WebElement element) {
		return element.getAttribute("value");
	}

    public void setElementText(WebElement element, String texto) {
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

	public boolean isMenuItemAvailable(String menu) {
		return isElementPresent(By.linkText(menu));
	}
	
	public void clickMenu(String menu) {
		driver.findElement(By.linkText(menu)).click();
	}

	public void clickButton(WebElement element) {
		element.click();
		Selenium.pausa(1000L);
	}

	public void exit() {
		driver.findElement(By.linkText("Sair")).click();
	}

    public String getTotalExibidos(WebElement element) {
    	String texto = element.getText();
    	String total = texto.substring(texto.indexOf('(')
    			, texto.indexOf(')')+1);
    	
    	String[] valorTotal = total.split(" ");
    	
		return valorTotal[4];
	}

	protected void filterTableByColumn(WebElement element, String value) {
		element.sendKeys(value);
	}
	
	protected boolean isPDFAvailable(WebElement paginator) {
		WebElement buttonPDF = paginator.findElement(
				By.xpath("a[2]/img[contains(@src"
				+ ",'/imovel/resources/img/pdf.png?pfdrid_c=true')]"));
		return buttonPDF.isDisplayed();
	}

	public boolean isExcelAvailable(WebElement paginator) {
		WebElement buttonExcel = paginator.findElement(
				By.xpath("a/img[contains(@src"
				+ ",'/imovel/resources/img/excel.png?pfdrid_c=true')]"));
		return buttonExcel.isDisplayed();
	}
	
	public boolean isTableEmpty(WebElement rows) {
		return rows.getText().equals(NENHUM_REGISTRO_ENCONTRADO);
	}
	
	public boolean isFilterAndSortTableAvailable(WebElement element) {
		return element.isDisplayed() 
				&& element.findElement(By.xpath("../span[2][contains(@class"
				+ ",'ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s')]"))
				.isDisplayed();
	}
	
}
