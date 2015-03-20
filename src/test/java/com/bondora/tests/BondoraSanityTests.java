package com.bondora.tests;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.bondora.exceptions.TechnicalException;
import com.bondora.exceptions.ValidationException;
import com.bondora.framework.HomePage;
import com.bondora.framework.UserPage;
import com.bondora.util.CommonUtils;
import com.bondora.util.SeleniumUtil;
import com.bondora.util.TestConstants;
import com.bondora.util.TestConstants.PORTFOLIO_MANAGER;


public class BondoraSanityTests {
	public static Logger log = Logger.getLogger(BondoraSanityTests.class);

	HomePage homePageObj;
	
	@BeforeSuite
	public void setup() throws TechnicalException {
		log.info("Start of setup");
		SeleniumUtil.setDriverObj(new FirefoxDriver());
		log.info("Load URL " + TestConstants.BONDORA_URL );
		SeleniumUtil.open(TestConstants.BONDORA_URL);
		SeleniumUtil.maximizeWindow();
		homePageObj = new HomePage();
		log.info("About to load UK- EN bondora site");
		homePageObj.goToEnglishHomePage();
		log.info("End of setup");
	}

//	@Test(description="Bondora page login") 
//	public void bondoraLogin() throws TechnicalException, FileNotFoundException, IOException, ValidationException {
//		log.info("Start of bondoraLogin");
//		log.info("Username : " + CommonUtils.readFromConfig("USERNAME"));
//		log.info("Password : " + CommonUtils.readFromConfig("PASSWORD"));
//		UserPage userObj = homePageObj.signIn(CommonUtils.readFromConfig("USERNAME"),CommonUtils.readFromConfig("PASSWORD"));	
//		Assert.assertTrue(userObj.isOnUserPage(),"User sign in is unsucessful.");
//		log.info("End of bondoraLogin");
//	}
	
	/**
	 * Test performs user login activity & verifies loading of ajax elements
	 * @throws TechnicalException
	 * @throws FileNotFoundException
	 * @throws ValidationException
	 * @throws IOException
	 */
	@Test(description="Target Allocation Validation") 
	public void targetAllocationValidation() throws TechnicalException, FileNotFoundException, ValidationException, IOException {
		log.info("Start of targetAllocationValidation");
		UserPage userObj = homePageObj.signIn(CommonUtils.readFromConfig("USERNAME"),CommonUtils.readFromConfig("PASSWORD"));
		log.info("User Sign in sucessful");
		Assert.assertTrue(userObj.isOnUserPage(),"User sign in is unsucessful.");
		log.info("Present in User Page");
		userObj.clickBalanced();
		log.info("Clicked Balanced Portfolio Manager");
		//Wait for the AJAX element to load and asserts accordingly
		Assert.assertTrue(userObj.isPieChartLoaded(),"Pie chart not loaded within the expected time");
		log.info("Pie chart loaded successfully");
		Assert.assertTrue(userObj.verifyDisplayOfTargetAllocation(PORTFOLIO_MANAGER.CONSERVATIVE), "The target Allocation chart did not load in expected timeout of " + TestConstants.LONG_WAIT + " seconds!");
		Assert.assertTrue(userObj.verifyDisplayOfTargetAllocation(PORTFOLIO_MANAGER.PROGRESSIVE), "The target Allocation chart did not load in expected timeout of " + TestConstants.LONG_WAIT + " seconds!");
		Assert.assertTrue(userObj.verifyDisplayOfTargetAllocation(PORTFOLIO_MANAGER.DYNAMIC), "The target Allocation chart did not load in expected timeout of " + TestConstants.LONG_WAIT + " seconds!");
		Assert.assertTrue(userObj.verifyDisplayOfTargetAllocation(PORTFOLIO_MANAGER.BALANCED), "The target Allocation chart did not load in expected timeout of " + TestConstants.LONG_WAIT + " seconds!");
		log.info("End of targetAllocationValidation");
	}
	
	@AfterSuite
	public void exit()  {
		log.info("In exit method");
		SeleniumUtil.close();
	}

}
