package com.suite.regression;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.adam.base.SuiteBase;
import com.utils.Constants;
import com.utils.Utils;

public class RegressionSuiteBase extends SuiteBase {
	public WebDriver driver;
	public WebDriverWait visibleElementWait, clickElementWait;
	public WebElement scheduleAll, queue, upload1, upload2;

	public void waitUntilElementVisible(int seconds, WebElement element) {
		visibleElementWait = new WebDriverWait(driver, seconds);
		visibleElementWait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitUntilElementClickable(int seconds, WebElement element){
		clickElementWait = new WebDriverWait(driver, seconds);
		clickElementWait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void dragAndDrop(WebElement sourceElement,
			WebElement destinationElement) {
		try {
			if (sourceElement.isDisplayed() && destinationElement.isDisplayed()) {
				Actions action = new Actions(driver);
				action.dragAndDrop(sourceElement, destinationElement).build()
						.perform();
			} else {
				System.out.println("Element not found to drag and drop");
			}
		} catch (StaleElementReferenceException se) {
			System.out
					.println("StaleElementReferenceException caught, try again!");
		} catch (NoSuchElementException ne) {
			System.out.println("NoSuchElementException caught, try again!");
		}
	}

	public WebElement getElementByXpath(String key) {
		try {
			return driver.findElement(By.xpath(OR.getProperty(key)));
		} catch (Throwable t) {
			return null;
		}
	}

	public WebElement getElementById(String key) {
		try {
			return driver.findElement(By.id(OR.getProperty(key)));
		} catch (Throwable t) {
			return null;
		}
	}

	public void runJobSupervisor() {
		queue = getElementByXpath("btnQueue");
		waitUntilElementClickable(5, queue);
		queue.click();
		scheduleAll = getElementByXpath("btnScheduleAllNow");
		waitUntilElementClickable(10, scheduleAll);
		scheduleAll.click();
		getElementByXpath("runJobSupervisor").click();
		try {
			Utils.threadSleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dropDownList(WebElement selectElement, String value){
		Select dropDown = new Select(selectElement);
		dropDown.selectByVisibleText(value);
	}
	
	@BeforeTest
	public void login() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(Constants.URL);
		readOR(Constants.PATHOBJECTREPO);
		getElementByXpath("textUserName").sendKeys(Constants.USERNAME);
		getElementByXpath("textPassWord").sendKeys(Constants.PASSWORD,
				Keys.ENTER);
	}

	@AfterTest
	public void logout() {
		getElementByXpath("btnAdministrator").click();
		getElementByXpath("btnLogout").click();
		driver.quit();
	}

}
