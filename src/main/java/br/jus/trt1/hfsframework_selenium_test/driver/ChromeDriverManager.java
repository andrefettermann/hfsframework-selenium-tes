package br.jus.trt1.hfsframework_selenium_test.driver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeDriverManager extends DriverManager {

	private ChromeDriverService service;
	
	private static ChromeDriverManager instance = new ChromeDriverManager();
	
	private ChromeDriverManager() {}
	
	public static ChromeDriverManager getInstance() {
		return instance;
	}

	@Override
	protected void startServices() {
		if (service == null) {
			try {
				service = new ChromeDriverService
						.Builder()
						.usingDriverExecutable(new File("chromedriver.exe"))
						.usingAnyFreePort().build();
				service.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void stopServices() {
		if (service != null && service.isRunning()) {
			service.stop();
		}

	}

	@Override
	protected void createDriver() {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("test-type");
		options.addArguments("--ignore-ssl-errors=yes");
		options.addArguments("--ignore-certificate-errors");
		options.addArguments("--disale-notifications");
		options.addArguments("--start-maximized");
		options.addArguments(
				"--disable-features=EnableEphemeralFlashPermission");
	    options.addArguments("--disable-infobars");
	    options.addArguments("--remote-allow-origins=*");
		
	    capabilities.setBrowserName("Chrome");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				//RemoteWebDriver(service.getUrl(), capabilities);
	}

}
