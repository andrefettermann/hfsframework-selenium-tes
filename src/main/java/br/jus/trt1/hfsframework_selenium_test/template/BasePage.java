/*
 * BasePage.java
 */
package br.jus.trt1.hfsframework_selenium_test.template;

import java.time.Duration;
import java.util.ArrayList;
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

import br.jus.trt1.hfsframework_selenium_test.utils.SeleniumUtils;

/**
 * Template base de pagina.
 * @author andre.fettermann - TRT1/STI/CSIS/DISAD
 */
public abstract class BasePage {

	public WebDriver driver;
	
	@FindBy(how = How.ID, using = "messages")
	protected WebElement mensagens;

	@FindBy(how = How.ID, using = "primefacesmessagedlg")
	private WebElement caixaDialogo;

	public BasePage(WebDriver driver) {
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
	private List<String> obtemMensagensExibidas() {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(2))
				.until(ExpectedConditions.visibilityOf(mensagens));
			String[] lines = mensagens.getText().split("\\r?\\n|\\r");
			return Arrays.asList(lines);
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
	 * @param input o elemento da pagina.
	 * @return o texto do atributo "value".
	 */
	public String obtemConteudoCampo(WebElement input) {
		return input.getAttribute("value");
	}

	/**
	 * Preenche o conteudo de um campo.
	 * @param input o elemento da pagina.
	 * @param texto o valor a ser preenchido no campo.
	 */
    public void preencheCampo(WebElement input, String texto) {
    	if (texto == null) texto = "";
    	input.clear();
    	input.sendKeys(texto);
    }
    
	/**
	 * Seleciona uma opcao do combo box pelo texto.
	 * 
	 * @param comboBox o elemento combo box.
	 * @param opcoes o elemento que contem as opcoes.
	 * @param opcao a opcao a ser selecionada.
	 */
	public void selecionaOpcaoComboBox(
			WebElement comboBox, WebElement opcoes, String opcao) {
		comboBox.click();

    	String cssSelector = "li[@data-label='" + opcao + "']";
		WebElement li = opcoes.findElement(
				By.xpath(cssSelector));
				//By.cssSelector(cssSelector));
    	li.click();
	}

	/**
	 * Retorna a lista de opcoes disponiveis do combo box.
	 * @param comboBox o elemento que representa o combo box.
	 * @param opcoes elemento com a lista de opcoes disponiveis.
	 * @return a lista com o texto das opcoes disponiveis.
	 */
	public List<String> obtemOpcoesComboBox(WebElement comboBox, 
			WebElement opcoes) {
		comboBox.click();
		List<String> lista = new ArrayList<>();
		for (WebElement li:opcoes.findElements(By.tagName("li"))) {
			lista.add(li.getText());
		}	
		return lista;
	}

	/**
	 * Retorna o texto do valor selecionado.
	 * @param comboBox o elemento combo box.
	 * @return o texto do valor selecionado no combo box.
	 */
	public String obtemValorSelecionadoComboBox(WebElement comboBox) {
		return comboBox.getText();
	}

	/**
	 * Obtem a mensagem exibida na caixa de dialogo e fecha a caixa de dialogo.
	 * @return o texto da mensagem exibida.
	 */
	public String obtemMensagemCaixaDialogo() {
		new WebDriverWait(driver, Duration.ofSeconds(2))
		.until(ExpectedConditions.visibilityOf(caixaDialogo));
	
		String mensagem = driver.findElement(
				By.xpath(
					"//div[@id='primefacesmessagedlg']/div[2]")).getText();		
		driver.findElement(
				By.xpath("//div[@id='primefacesmessagedlg']/div/a")).click();
		SeleniumUtils.pausa(1000L);
		return mensagem;
	}


	/**
	 * Sai do sistema clicando em "Sair" no menu superior.
	 */
	public void sair() {
		driver.findElement(By.linkText("Sair")).click();
	}

	/**
	 * Verifica se o check box esta marcado.
	 * 
	 * @param checkBox o elemento da pagina.
	 * @return verdadeiro se o elemento estiver marcado e falso se nao estiver
	 * marcado.
	 */
	protected boolean isCheckBoxMarcado(WebElement checkBox) {
		return checkBox.getAttribute("class").contains("ui-icon-check");
	}

	/**
	 * Verifica se o check box esta desabilitado.
	 * 
	 * @param checkBox o elemento da pagina.
	 * @return verdadeiro se o elemento estiver desabilitado e falso se nao 
	 * estiver.
	 */
	protected boolean isCheckBoxDesabilitado(WebElement checkBox) {
		return checkBox.getAttribute("class").contains("ui-state-disabled");
	}

	protected boolean isElementoHabilitado(WebElement elemento) {
		return elemento.getAttribute("class").contains("ui-state-enabled");
	}
	
	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
