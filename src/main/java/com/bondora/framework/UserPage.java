package com.bondora.framework;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import com.bondora.exceptions.TechnicalException;
import com.bondora.util.SeleniumUtil;
import com.bondora.util.TestConstants;
import com.bondora.util.TestConstants.PORTFOLIO_MANAGER;

public class UserPage {

	public static Logger log = Logger.getLogger(UserPage.class);
	
	private final By logoutlink = By.cssSelector("a[href='/en/FormsAuthentication/logout']");
	private final By conservative = By.cssSelector("div[id='1']");
	private final By balanced = By.cssSelector("div[id='2']");
	private final By progressive = By.cssSelector("div[id='3']");
	private final By dynamic = By.cssSelector("div[id='0']");
	private final By targetAllocationPieChart = By.cssSelector("table[class='table-folio'] tr[class='table-folio-small-th'] th[class='table-folio-graph'] div[id='pie-chart'] svg");

	public void clickConservative() throws TechnicalException {
		SeleniumUtil.click(conservative);
	}

	public void clickBalanced() throws TechnicalException {
		SeleniumUtil.click(balanced);
	}

	public void clickProgressive() throws TechnicalException {
		SeleniumUtil.click(progressive);
	}

	public void clickDynamic() throws TechnicalException {
		SeleniumUtil.click(dynamic);
	}

	
	/**
	 * Verifies if present on User Page
	 * @return
	 */
	public boolean isOnUserPage() {
		if(SeleniumUtil.isElementPresent(logoutlink)) {
			SeleniumUtil.waitForPageLoad();
			log.info("Present in User home page");
			return true;
		} else {
			log.error("Not present in User home page");
			return false;
		}	 
	}

	/**
	 * Assumes to be present on user page
	 * Clicks portfolio manager section in user page based on the provided input parameter
	 * @param portfolioManager
	 * @throws TechnicalException
	 */
	public void selectPortfolioManager(TestConstants.PORTFOLIO_MANAGER portfolioManager) throws TechnicalException {
		if(PORTFOLIO_MANAGER.CONSERVATIVE.equals(portfolioManager)){
			clickConservative();
		} else if (PORTFOLIO_MANAGER.BALANCED.equals(portfolioManager)){
			clickBalanced();
		} else if (PORTFOLIO_MANAGER.PROGRESSIVE.equals(portfolioManager)){
			clickProgressive();
		} else {
			clickDynamic();
		}
	}

	/**
	 * Verifies if the target allocation is displayed(waits for ajax element to load) for the portfolio manager selected
	 * Verifies by comparing the number of divisions present in the pie chart before and after the ajax call
	 * @param portfolioManager
	 * @return
	 * @throws TechnicalException
	 */
	public boolean verifyDisplayOfTargetAllocation(TestConstants.PORTFOLIO_MANAGER portfolioManager) throws TechnicalException {
		log.info("Start of verifyDisplayOfTargetAllocation");
		int noOfPieChartElementsBeforeAjaxCall = 0, noOfPieChartElementsAfterAjaxCall = 0;
		boolean result = false;

		SeleniumUtil.waitForPageToLoadElement(targetAllocationPieChart, TestConstants.LONG_WAIT, TestConstants.ONE_SECOND);
		log.info("Pie chart element is present!!");
		WebElement pieChartElement = SeleniumUtil.findElement(targetAllocationPieChart);
		noOfPieChartElementsBeforeAjaxCall = pieChartElement.findElements(By.cssSelector("g")).size();
		log.info("Pie chart elements currently present is " + noOfPieChartElementsBeforeAjaxCall);
		selectPortfolioManager(portfolioManager);
		for(int count=0;count<TestConstants.LONG_WAIT;count++) {
			SeleniumUtil.waitInSeconds(TestConstants.ONE_SECOND);
			pieChartElement = SeleniumUtil.findElement(targetAllocationPieChart);
			noOfPieChartElementsAfterAjaxCall = pieChartElement.findElements(By.cssSelector("g")).size();
			log.info("Pie chart elements present after ajax call  is " + noOfPieChartElementsAfterAjaxCall);
			if(noOfPieChartElementsBeforeAjaxCall!=noOfPieChartElementsAfterAjaxCall) {
				log.info("Ajax response received!! Elements present before are : " + noOfPieChartElementsBeforeAjaxCall + 
						" After ajax call elements present are : " + noOfPieChartElementsAfterAjaxCall);
				result = true;
				break;
			}
		}
		log.info("End of verifyDisplayOfTargetAllocation");
		return result; 
	}
	
	/**
	 * Verifies if the Pie chart is loaded within a timeout period
	 * @return
	 */
	public boolean isPieChartLoaded() { 
		try	{
			SeleniumUtil.waitForPageToLoadElement(targetAllocationPieChart, TestConstants.LONG_WAIT, TestConstants.ONE_SECOND);
		} catch(TimeoutException e) {
			return false;
		}
		return true;
	}

}
