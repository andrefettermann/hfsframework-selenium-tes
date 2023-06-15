/*
 * ComboBox.java
 */
package br.jus.trt1.hfsframework_selenium_test.model;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Classe utilitaria para campos do combo check box da pagina.
 * 
 * @author andre.fettermann - TRT1/STI/CSIS/DISAD
 */
public class ComboBox {
	
	/**
	 * Clica no elemento.
	 * @param elemento o elemento da pagina.
	 */
	public void clica(WebElement elemento) {
		elemento.click();
	}
	
	/**
	 * Seleciona uma opcao do combo box pelo texto.
	 * 
	 * @param comboBox o elemento combo box.
	 * @param opcoes o elemento que contem as opcoes.
	 * @param opcao a opcao a ser selecionada.
	 */
	public void seleciona(WebElement comboBox, WebElement opcoes, String opcao) {
    	String cssSelector = "li[data-label*='" + opcao + "']";
		WebElement li = opcoes.findElement(
				By.cssSelector(cssSelector));
    	li.click();
	}

	/**
	 * Retorna a lista de opcoes disponiveis do combo box.
	 * @param elemento elemento com a lista de opcoes disponiveis.
	 * @return a lista com o texto das opcoes disponiveis.
	 */
	public List<String> opcoesDisponiveis(WebElement elemento) {
		List<String> lista = new ArrayList<>();
		for (WebElement li:elemento.findElements(By.tagName("li"))) {
			lista.add(li.getText());
		}	
		return lista;
	}
	
	/**
	 * Retorna o texto do valor selecionado.
	 * @param elemento o elemento combo box.
	 * @return o texto do valor selecionado no combo box.
	 */
	public String valorSelecionado(WebElement elemento) {
		return elemento.getText();
	}
}
