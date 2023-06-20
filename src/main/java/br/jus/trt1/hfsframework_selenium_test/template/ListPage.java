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
	private WebElement caixaDialogo;

	public ListPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public abstract void abre();
	
	/**
	 * Obtem o titulo exibido no navegador.
	 * @return o texto com o titulo.
	 */
    public String obtemTituloPagina() {
    	return driver.getTitle().replaceAll("\u00A0", " ");
    }
    
    /**
     * Obtem o total de mensagens exibidas.
     * @return o total de mensagens exibidas.
     */
    public int obtemTotalMensagensExibidas() {
    	return obtemMensagensExibidas().size();
    }
    
    /**
     * Verifica se a mensagem foi exibida.
     * @param mensagem a mensagem.
     * @return true se foi exibida e false se nao foi exibida.
     */
    public boolean isMensagemExibida(String mensagem) {
    	return obtemMensagensExibidas().contains(mensagem);
    }
    
    /**
     * Obtem as mensagens exibidas na pagina.
     * @return lista com o texto das mensagens exibidas.
     */
	public List<String> obtemMensagensExibidas() {
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

	/**
	 * Tira um print screen da tela.
	 * @return o print screen da tela.
	 */
	public byte[] takeScreenShot() {
		final byte[] screenshot = 
				((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		return screenshot;
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
		return mensagem;
	}

	/**
	 * Obtem o conteudo do campo.
	 * @param elemento o campo
	 * @returnum texto com o conteudo do campo.
	 */
	protected String obtemConteudoCampo(WebElement elemento) {
		return elemento.getAttribute("value");
	}

	/**
	 * Preenche o campo.
	 * @param elemento o campo.
	 * @param valor o texto a ser preenchido no campo.
	 */
    protected void preencheCampo(WebElement elemento, String valor) {
    	elemento.clear();
    	elemento.sendKeys(valor);
    }
    
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Verifica se o item de menu esta disponivel na pagina
	 * @param valor o label do item de menu.
	 * @return verdadeiro se estiver e falso se nao estiver.
	 */
	public boolean isMenuItemAvailable(String valor) {
		return isElementPresent(By.linkText(valor));
	}
	
	/**
	 * Clica no menu.
	 * @param valor o label do item do menu.
	 */
	public void clicaMenu(String valor) {
		driver.findElement(By.linkText(valor)).click();
	}

	/**
	 * Clica no elemento e aguarda 1 segundo.
	 * @param elemento o elemento a ser clicado.
	 */
	protected void clica(WebElement elemento) {
		elemento.click();
		Selenium.pausa(1000L);
	}

	/**
	 * Sai do sistema clicando em "Sair" no menu superior.
	 */
	public void sair() {
		driver.findElement(By.linkText("Sair")).click();
	}

	/**
	 * Obtem o total de registros carregados a partir do valor exibido
	 * no paginador da tabela.
	 * @param paginador o paginador da tabela.
	 * @return o total exibido.
	 */
    protected String obtemTotalRegistrosTabela(WebElement paginador) {
    	String texto = paginador.getText();
    	String total = texto.substring(texto.indexOf('(')
    			, texto.indexOf(')')+1);
    	
    	String[] valor = total.split(" ");
    	
		return valor[4];
	}

    /**
     * Preenche o campo do cabecalho da coluna da tabela para filtrar os dados 
     * e aguarda 1 segundo.
     * @param coluna o cmapo da coluna da tabela.
     * @param valor o valor a ser filtrado.
     */
	protected void preencheCabecalhoColunaTabela(WebElement coluna, String valor) {
		coluna.sendKeys(valor);
		Selenium.pausa(1000L);
	}
	
	/**
	 * Seleciona a 1 linha da tabela e aguarda 1 segundo.
	 * @param linhas o elemento com as lonhas da tabela.
	 */
	protected void selecionaLinhaTabela(WebElement linhas) {
		linhas.findElement(By.xpath("tr")).click();
		Selenium.pausa(1000L);
	}
	
	/**
	 * Verifica se e possivel exportar para PDF os dados da tabela.
	 * @param paginador o elemento do paginador da tabela.
	 * @return verdadeiro se o botao estiver disponivel e falso se nao estiver.
	 */
	protected boolean isExportarPDFDisponivel(WebElement paginador) {
		WebElement buttonPDF = paginador.findElement(
				By.xpath("a[2]/img[contains(@src"
				+ ",'/imovel/resources/img/pdf.png?pfdrid_c=true')]"));
		return buttonPDF.isDisplayed();
	}

	/**
	 * Verifica se e possivel exportar para Excel os dados da tabela.
	 * @param paginador o elemento do paginador da tabela.
	 * @return verdadeiro se o botao estiver disponivel e falso se nao estiver.
	 */
	protected boolean isExportarExcelDisponivel(WebElement paginador) {
		WebElement buttonExcel = paginador.findElement(
				By.xpath("a/img[contains(@src"
				+ ",'/imovel/resources/img/excel.png?pfdrid_c=true')]"));
		return buttonExcel.isDisplayed();
	}
	
	/**
	 * Verifica se a tabela esta vazia.
	 * @param dados o elemento com as linhas da tabela.
	 * @return verdadeiro se foi exibido que nenhum registro foi encontrado e 
	 * falso se nao foi exibido.
	 */
	protected boolean isTabelaVazia(WebElement dados) {
		return dados.getText().equals(NENHUM_REGISTRO_ENCONTRADO);
	}
	
	/**
	 * Verifica se existe o campo para informar os dados a serem filtrados e 
	 * a seta ao lado do nome da coluna estao disponiveis.
	 * 
	 * @param coluna o elemento do cabecalho da coluna.
	 * @return verdadeiro se for possivel e falso se nao for possivel.
	 */
	protected boolean isFiltrarOrdenarColunaDisponivel(WebElement coluna) {
		return coluna.isDisplayed() 
				&& coluna.findElement(By.xpath("../span[2][contains(@class"
				+ ",'ui-sortable-column-icon ui-icon ui-icon-carat-2-n-s')]"))
				.isDisplayed();
	}
	
}
