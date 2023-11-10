package br.jus.trt1.hfsframework_selenium_test.driver;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import br.jus.trt1.hfsframework_selenium_test.utils.SeleniumUtils;

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
			
			try {
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
				SeleniumUtils.pausa(2000L);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
