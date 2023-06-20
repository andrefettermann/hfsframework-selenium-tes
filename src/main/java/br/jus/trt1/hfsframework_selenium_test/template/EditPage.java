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
 * Template de pagina de edicao de dados.
 * @author andre.fettermann - TRT1/STI/CSIS/DISAD
 */
public abstract class EditPage {

	public WebDriver driver;
	
	@FindBy(how = How.ID, using = "messages")
	protected WebElement mensagens;

	@FindBy(how = How.ID, using = "primefacesmessagedlg")
	private WebElement alerta;

	public EditPage(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * Retorna o titulo exibido na barra do navegador.
	 * @return o texto do titulo.
	 */
    public String obtemTituloPagina() {
    	return driver.getTitle().replaceAll("\u00A0", " ");
    }
    
    /**
     * Obtem a quantidade de mensagens exibidas.
     * @return a quantidade.
     */
    public int obtemQuantidadeMensagensExibidas() {
    	return obtemMensagensExibidas().size();
    }
    
    /**
     * Verifica se a mensagem foi exibida.
     * @param mensagem o texto da mensagem.
     * @return true se foi exibida e false se nao foi exibida.
     */
    public boolean isMensagemExibida(String mensagem) {
    	return obtemMensagensExibidas().contains(mensagem);
    }
    
    /**
     * Retorna as mensagens exibidas na pagina.
     * @return lista com os textos das mensagens.
     */
	public List<String> obtemMensagensExibidas() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(2))
				.until(ExpectedConditions.visibilityOf(mensagens));
			String[] lines = mensagens.getText().split("\\r?\\n|\\r");
			return Arrays.asList(lines);
					//mensagens.getText();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * Tira um print da pagina.
	 * @return o print da pagina.
	 */
	public byte[] takeScreenShot() {
		final byte[] screenshot = 
				((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		return screenshot;
	}

	/**
	 * Retorna o conteu do campo.
	 * @param elemento o elemento da pagina.
	 * @return o texto do atributo "value".
	 */
	public String obtemConteudoCampo(WebElement elemento) {
		return elemento.getAttribute("value");
	}

	/**
	 * Preenche o conteudo de um campo.
	 * @param elemento o elemento da pagina.
	 * @param texto o valor a ser preenchido no campo.
	 */
    public void preencheCampo(WebElement elemento, String texto) {
    	if (texto == null) texto = "";
    	elemento.clear();
    	elemento.sendKeys(texto);
    }
    
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public abstract void grava();
	
	public abstract void cancela();
	
	protected void clica(WebElement element) {
		element.click();
	}

}
