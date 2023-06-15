package br.jus.trt1.hfsframework_selenium_test.driver;

public class DriverManagerFactory {

	private DriverManager driverManager;

	private static DriverManagerFactory instance = new DriverManagerFactory();

	public static DriverManagerFactory getInstance() {
		return instance;
	}

	private DriverManagerFactory() {
	}

	public DriverManager getManager(DriverType type) {
		switch (type) {
		case CHROME:
			driverManager = ChromeDriverManager.getInstance();
			break;
		case HTML_UNIT:
			//driverManager = new HTMLUnitDriverManager();
			break;
		case FIREFOX:
			// driverManager = new FirefoxDriverManager();
			break;
		default:
			// driverManager = new SafariDriverManager();
			break;
		}
		return driverManager;
	}
}
