package com.suite.regression;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.adam.base.SuiteBase;
import com.utils.Utils;

public class RegressionSuiteBase extends SuiteBase {
	public WebDriver driver;

	public void waitUntilElementVisible(int seconds, WebElement element) {
		WebDriverWait visibleElementWait = new WebDriverWait(driver, seconds);
		visibleElementWait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitElementClickable(int seconds, WebElement element){
		WebDriverWait clickElementWait = new WebDriverWait(driver, seconds);
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
		getElementByXpath("btnQueue").click();
		getElementByXpath("scheduleAllNow").click();
		getElementByXpath("runJobSupervisor").click();
		try {
			Utils.threadSleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
