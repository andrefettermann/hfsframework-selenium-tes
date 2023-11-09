/*
 * ListPage.java
 */
package br.jus.trt1.hfsframework_selenium_test.template;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import br.jus.trt1.hfsframework_selenium_test.utils.SeleniumUtils;

/**
 * List page template.
 * @author andre.fettermann - TRT1/DISAD
 */
public abstract class ListPage extends BasePage {

	public static final String NENHUM_REGISTRO_ENCONTRADO = 
			"Nenhum registro encontrado.";
	
	public static final String SELECIONE = 
			"Favor selecionar um registro da tabela para proceder esta ação.";
	
	public static final String MENSAGEM_HA_IMOVEIS_ASSOCIADOS = 
			"Não é possível excluir esta entidade, pois ela está associada a "
			+ "pelo menos outra entidade.";


	public ListPage(WebDriver driver) {
		super(driver);
	}
	
	public abstract void seleciona();
	
	/**
	 * Obtem o total de registros carregados a partir do valor exibido
	 * no paginador da tabela.
	 * @param paginador o paginador da tabela.
	 * @return o total exibido.
	 */
    protected Integer obtemTotalRegistrosTabela(WebElement paginador) {
    	String texto = paginador.getText();
    	String total = texto.substring(texto.indexOf('(')
    			, texto.indexOf(')')+1);
    	
    	String[] valor = total.split(" ");
    	
		return Integer.parseInt(valor[4]);
	}

    /**
     * Preenche o campo do cabecalho da coluna da tabela para filtrar os dados 
     * e aguarda 1 segundo.
     * @param coluna o cmapo da coluna da tabela.
     * @param valor o valor a ser filtrado.
     */
	protected void filtraTabelaPorColuna(WebElement coluna, String valor) {
		coluna.clear();
		coluna.sendKeys(valor);
		SeleniumUtils.pausa(1000L);
	}
	
	/**
	 * Seleciona a 1 linha da tabela e aguarda 1 segundo.
	 * @param linhas o elemento com as lonhas da tabela.
	 */
	protected void selecionaLinhaTabela(WebElement linhas) {
		linhas.findElement(By.xpath("tr")).click();
		SeleniumUtils.pausa(1000L);
	}
	
	/**
	 * Confirma ou nao a exclusao.
	 * @param caixaDialogo a caixa de dialogo para confirmar.
	 * @param confirma true indica que a exclusao deve ser confirmada e false
	 * que a exclusao nao deve ser confirmada.
	 */
	public void defineConfirmaExclusao(
			WebElement caixaDialogo, boolean confirma) {
		new WebDriverWait(driver, Duration.ofSeconds(2))
		.until(ExpectedConditions.visibilityOf(caixaDialogo));

		if (confirma) {
			caixaDialogo.findElement(
				By.xpath("div[3]/button[1]")).click();
		} else {
			caixaDialogo.findElement(
					By.xpath("div[3]/button[2]")).click();			
		}
		SeleniumUtils.pausa(1000L);
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
	
	public boolean isMensagemEntidadeAssociadaExibida() {
		return obtemMensagemCaixaDialogo()
				.equals(MENSAGEM_HA_IMOVEIS_ASSOCIADOS);
	}
	
}
