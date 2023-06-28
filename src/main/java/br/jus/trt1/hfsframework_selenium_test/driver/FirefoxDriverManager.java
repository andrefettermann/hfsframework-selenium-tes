package br.jus.trt1.hfsframework_selenium_test.driver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FirefoxDriverManager extends DriverManager {

	private GeckoDriverService service;
	
	private static FirefoxDriverManager instance = new FirefoxDriverManager();
	
	private FirefoxDriverManager() {}
	
	public static FirefoxDriverManager getInstance() {
		return instance;
	}
 
	@Override
	protected void startServices() {
		if (service == null) {
			try {
				service =  new GeckoDriverService.Builder()
						.usingDriverExecutable(new File("geckodriver.exe"))
						.usingAnyFreePort()
						.build();
				
						//.Builder()
						//.usingDriverExecutable(new File("chromedriver.exe"))
						//.usingAnyFreePort().build();
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
		
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--headless");
		//options.setHeadless(Boolean.valueOf(System.getProperty("headless")));
		//options.addArguments("test-type");
		//options.addArguments("--ignore-ssl-errors=yes");
		//options.addArguments("--ignore-certificate-errors");
		//options.addArguments("--disale-notifications");
		//options.addArguments("--start-maximized");
		//options.addArguments(
		//		"--disable-features=EnableEphemeralFlashPermission");
	    //options.addArguments("--disable-infobars");
	    //options.addArguments("--remote-allow-origins=*");
		
	    capabilities.setBrowserName("Firefox");
		capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
		driver = new FirefoxDriver(options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
				//RemoteWebDriver(service.getUrl(), capabilities);
	}

}
