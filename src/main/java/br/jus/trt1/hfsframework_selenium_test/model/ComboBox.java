package br.jus.trt1.hfsframework_selenium_test.model;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ComboBox {
	
	public void open(WebElement element) {
		element.click();
	}
	
	public void select(WebElement comboBox, WebElement options, String value) {
    	String cssSelector = "li[data-label*='" + value + "']";
		WebElement option = options.findElement(
				By.cssSelector(cssSelector));
    	option.click();
	}

	public List<String> getAvailableOptions(WebElement options) {
		List<String> optionsList = new ArrayList<>();
		for (WebElement option:
			options.findElements(By.tagName("li"))) {
			optionsList.add(option.getText());
		}	
		return optionsList;
	}
	
	public String getSelectedValue(WebElement comboBox) {
		return comboBox.getText();
	}
}
