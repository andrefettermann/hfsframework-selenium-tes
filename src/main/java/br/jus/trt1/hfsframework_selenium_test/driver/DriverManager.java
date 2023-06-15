package br.jus.trt1.hfsframework_selenium_test.driver;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public abstract class DriverManager {
	
	static Logger LOGGER = Logger.getLogger(DriverManager.class.getName());

	protected WebDriver driver;
	protected abstract void startServices();
	protected abstract void stopServices();
	protected abstract void createDriver();
	
	public void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}
	
	public WebDriver getDriver() {
		if (driver == null) {
			startServices();
			createDriver();
			//stopServices();
		}
		
		return driver;
	}
}
