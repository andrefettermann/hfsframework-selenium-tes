/*
 * CheckBox.java
 */
package br.jus.trt1.hfsframework_selenium_test.model;

import org.openqa.selenium.WebElement;

/**
 * Classe utilitaria para campos do tipo check box da pagina.
 * 
 * @author andre.fettermann - TRT1/STI/CSIS/DISAD
 */
public class CheckBox {
	
	/**
	 * Clica no elemento.
	 * 
	 * @param elemento o elemento da pagina.
	 */
	public void clica(WebElement elemento) {
		elemento.click();
	}

	/**
	 * Verifica se o elemento esta marcado.
	 * 
	 * @param elemento o elemento da pagina.
	 * @return verdadeiro se o elemento estiver marcado e falso se nao estiver
	 * marcado.
	 */
	public boolean isMarcado(WebElement elemento) {
		return elemento.getAttribute("class")
				.contains("ui-icon-check");
	}
	
	/**
	 * Verifica se o elemento esta desabilitado.
	 * 
	 * @param elemento o elemento da pagina.
	 * @return verdadeiro se o elemento estiver desablitado e falso se estiver
	 * habilitado.
	 */
	public boolean isDesabilitado(WebElement elemento) {
		return elemento.getAttribute("class").contains("ui-state-disabled");
	}
}
