package com.bondora.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bondora.exceptions.TechnicalException;


/**
 * Has selenium methods
 *
 */
public class SeleniumUtil {
	public static Logger log = Logger.getLogger(SeleniumUtil.class);

	private static WebDriver driver ;

	public static void setDriverObj(WebDriver driverObj) {
		driver = driverObj;
	}

	/**
	 * Maximizes the driver window
	 * 
	 */
	public static void maximizeWindow() {
		driver.manage().window().maximize();
	}

	/**
	 * Launches the given URL
	 */
	public static void open(String url) {
		driver.get(url);
	}

	public static void close() {
		driver.quit();
	}

	/**
	 * @param uiLocator
	 * @return
	 */
	public static WebElement findElement(By uiLocator) {
		WebElement element = null;
		try {
			element = driver.findElement(uiLocator);
		} catch (NoSuchElementException e) {
			log.info("findElement.element not found for locator : " + uiLocator);
		} catch (ElementNotVisibleException e) {
			log.info("findElement.element not visible for locator : " + uiLocator);
		}
		return element;
	}	

	/**
	 * @param uiLocator
	 * @throws TechnicalException
	 */
	public static void click(By uiLocator) throws TechnicalException {
		WebElement element = findElement(uiLocator);
		if(element!=null)
			element.click();
		else 
			throw new TechnicalException("Element not found to click " + uiLocator);
	}

	/**
	 * @param uiLocator
	 * @param text
	 */
	public static void sendKeys(By uiLocator,String text) throws TechnicalException {
		WebElement element = findElement(uiLocator);
		if(element!=null) {
			element.clear();
			element.sendKeys(text);
		} else {
			throw new TechnicalException("Element not found to sendKeys " + uiLocator);
		}
	}

	/**
	 * This method would wait till the document.readyState is changed to complete or timeouts
	 * @param wait
	 * @throws InterruptedException
	 */
	public static void waitForPageLoad(long wait) {
		JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
		String status = (String) javaScriptExecutor.executeScript("return document.readyState");
		log.info("Status of the page : " + status);
		long waittime = wait * 1000;
		long starttime = System.currentTimeMillis();
		try {
			while (!status.equals("complete") && System.currentTimeMillis() < starttime + waittime) {
				status = (String) javaScriptExecutor.executeScript("return document.readyState");
				log.info("Status of the page  in the while loop : " + status);
				Thread.sleep(TestConstants.MINI_WAIT * 400);
			}
		} catch (InterruptedException e) {
			log.error(e);
		}
		if (!status.equals("complete"))
			log.error("The page has taken more than " + wait + " seconds load");
	}
	
	/**
	 * @param timeOutInSeconds
	 */
	public static void waitInSeconds(long timeOutInSeconds) {
		try {
			log.info("Sleeping for " + timeOutInSeconds + " seconds. ");
			Thread.sleep(timeOutInSeconds*1000);
		} catch (InterruptedException e) {
			log.error(e);
		}
	}

	/**
	 * Waits for page to load with a timeout defined in TestConstants
	 */
	public static void waitForPageLoad() {
		waitForPageLoad(TestConstants.LONG_WAIT);
	}

	/**
	 * Waits for a page to load a specific element
	 * @param uiLocator
	 * @param timeOutInSeconds
	 * @param sleepInMillis
	 */
	public static void waitForPageToLoadElement(By uiLocator, long timeOutInSeconds, long sleepInSeconds) {		
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds,sleepInSeconds*1000);
		wait.until(ExpectedConditions.presenceOfElementLocated(uiLocator));	
	}

	/**
	 * Return true if an element is present else returns false
	 * @param webElementLocator
	 * @return
	 */
	public static boolean isElementPresent(By uiLocator) {
		try {
			driver.findElement(uiLocator);
			return true;
		} catch (NoSuchElementException e) {
			log.info("isElementPresent.Element not present for:" + uiLocator);
			return false;
		} catch (Exception e) {
			log.info("isElementPresent.Other exception :" + uiLocator + " Exception:" + e.getMessage());
			return false;
		}
	}
	
}
