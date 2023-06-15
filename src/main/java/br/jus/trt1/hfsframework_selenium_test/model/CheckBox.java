package br.jus.trt1.hfsframework_selenium_test.model;

import org.openqa.selenium.WebElement;

public class CheckBox {
	
	public void click(WebElement element) {
		element.click();
	}

	public boolean isChecked(WebElement element) {
		return element.getAttribute("class")
				.contains("ui-icon-check");
	}
	
	public boolean isDisabled(WebElement element) {
		return element.getAttribute("class").contains("ui-state-disabled");
	}
}
