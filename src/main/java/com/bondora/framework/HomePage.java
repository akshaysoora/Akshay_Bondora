package com.bondora.framework;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;

import com.bondora.exceptions.TechnicalException;
import com.bondora.exceptions.ValidationException;
import com.bondora.util.SeleniumUtil;
import com.bondora.util.TestConstants;


public class HomePage {

	public static Logger log = Logger.getLogger(HomePage.class);

	private final By ukENLangLink = By.cssSelector("a[href='https://www.bondora.co.uk/en/home']");
	private final By signInLink = By.linkText("SECURE SIGN-IN");
	private final By userNameField = By.id("username");
	private final By passwordField = By.id("password");
	private final By loginBtn = By.cssSelector("div[class='sign-in'] button[type='submit']");
	private final By loginErrorMsg = By.className("field-validation-error");

	public void clickUkENLangLink() throws TechnicalException {
		SeleniumUtil.click(ukENLangLink);
	}

	public void clickOnSignInLink() throws TechnicalException {
		SeleniumUtil.click(signInLink);
	}

	public void clickLoginBtn() throws TechnicalException {
		SeleniumUtil.click(loginBtn);
	}

	public void goToEnglishHomePage() throws TechnicalException { 
		SeleniumUtil.waitForPageToLoadElement(ukENLangLink, TestConstants.MEDIUM_WAIT, TestConstants.ONE_SECOND);
		clickUkENLangLink();
		SeleniumUtil.waitForPageLoad();
	}

	public void enterUsername(String text) throws TechnicalException {
		SeleniumUtil.sendKeys(userNameField, text);
	}

	public void enterCredentials(String usernameText, String passwordText) throws TechnicalException {
		enterUsername(usernameText);
		enterPassword(passwordText);
	}

	public void enterPassword(String text) throws TechnicalException {
		SeleniumUtil.sendKeys(passwordField, text);

	}

	public boolean isSignInUnSucessfull() {
		return SeleniumUtil.isElementPresent(loginBtn);

	}

	/**
	 * Performs sign in operation
	 * @param usernameText
	 * @param passwordText
	 * @return
	 * @throws TechnicalException
	 * @throws ValidationException
	 */
	public UserPage signIn(String usernameText, String passwordText) throws TechnicalException, ValidationException { 
		clickOnSignInLink();
		enterCredentials(usernameText,passwordText);
		clickLoginBtn();
		SeleniumUtil.waitForPageLoad();
		if(isSignInUnSucessfull()) {
			if(SeleniumUtil.isElementPresent(loginErrorMsg)) {
				throw new ValidationException(SeleniumUtil.findElement(loginErrorMsg).getText());
			} else {
			throw new ValidationException("Login Unsucessful");
			}
		} else {
			return new UserPage();
		}

	}
}

