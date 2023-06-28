/*
 * Selenium.java
 */
package br.jus.trt1.hfsframework_selenium_test.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.jus.trt1.hfsframework_selenium_test.driver.DriverType;


/**
 * Metodos utilitarios do Selenium.
 * 
 * @author andre.fettermann - TRT1/DISAD
 *
 */
public final class SeleniumUtils {

	private static WebDriver driver;
	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	
	private static final String FOLDER = "./target/testes_unitarios";
	
	private static boolean logado;

	/**
	 * Registra que o usuario esta logado no sistema.
	 * @param status verdadeiro ou falso.
	 */
	public static void setLogado(boolean status) {
		logado = status;
	}
	
	/**
	 * Informa se o usuario esta logado no sistema.
	 * @return verdadeiro ou falso.
	 */
	public static boolean isLogado() {
		return logado;
	}
	
	/**
	 * Retorna o webdriver.
	 * @param type o navegador que sera usado.
	 * @return o webdriver para o navegador.
	 */
	public static WebDriver getDriver(DriverType type) {
		if (driver == null) {
			//driver = DriverFactory.getDriver(type);
		}

		return driver;
	}
	
	/**
	 * Pausa o processamento pelo tempo informado.
	 * 
	 * @param tempoMils
	 *            Tempo para pausa em milisegundos(segundos * mil)
	 */
	public static void pausa(long tempoMils) {
		try {
			Thread.sleep(tempoMils);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String decodeUTF8(byte[] bytes) {
		return new String(bytes, UTF8_CHARSET);
	}

	public static byte[] encodeUTF8(String string) {
		return string.getBytes(UTF8_CHARSET);
	}

	public static byte[] encodeISO(String string) {
		return string.getBytes(Charset.forName("ISO-8859-1"));
	}

	public static String decodeISO(byte[] bytes) {
		return new String(bytes, Charset.forName("ISO-8859-1"));
	}

	/**
	 * Rola a pagina ate o elemento estar visivel.
	 * @param element o elemento que deve se tornar visivel.
	 */
	public static void scrollIntoView(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView()", element);
	}
	
	/**
	 * Rola a pagina para cima 400 pixels.
	 */
	public static void scrollUp() {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-400)", "");
	}
	
	/**
	 * Rola a pagina para baixo 400 pixels.
	 */
	public static void scrollDown() {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)", "");
	}

	public static String takeScreenShot() throws IOException {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String fileName = //UUID.randomUUID().toString();
        		"screenshot-" + timeStampMillis;
        
        File targetFile = new File(FOLDER + File.separatorChar + fileName + ".png");
        
        FileUtils.copyFile(scrFile, targetFile);
        
        return targetFile.getCanonicalPath();
    }

	/**
	 * Exclui todos os cookies, fecha o navegador e encerra a execucao do driver.
	 */
	public static void tearDown() {
		driver.manage().deleteAllCookies();
		//driver.close();
		driver.quit();
	}

}
