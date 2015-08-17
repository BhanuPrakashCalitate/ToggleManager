package com.suite.regression;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.utils.Constants;
import com.utils.Utils;

public class Users extends RegressionSuiteBase {

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

	public void userFormPage() {
		getElementByXpath("btnUser").click();
		getElementByXpath("btnAddDropDown").click();
		getElementByXpath("btnAddUser").click();
	}

	@Test(priority = 1)
	public void createUser() {
		userFormPage();
		getElementByXpath("textUsersName").sendKeys(
				"testuser" + Utils.randGen());
		getElementByXpath("textFirstName").sendKeys("test" + Utils.randGen());
		getElementByXpath("textLastName").sendKeys("user" + Utils.randGen());
		getElementByXpath("textEmail").sendKeys(
				"testemail" + Utils.randGen() + "@openpeak.com");
		dropDownList(getElementById("dropDownSelect"), "India");
		getElementByXpath("btnScheduleNow").click();
	}

	@Test(priority = 2)
	public void cancelCreatingUser() {
		userFormPage();
		getElementByXpath("textUsersName").sendKeys(
				"testuser" + Utils.randGen());
		getElementByXpath("textFirstName").sendKeys("test" + Utils.randGen());
		getElementByXpath("textLastName").sendKeys("user" + Utils.randGen());
		getElementByXpath("textEmail").sendKeys(
				"testemail" + Utils.randGen() + "@openpeak.com");
		dropDownList(getElementById("dropDownSelect"), "India");
		getElementByXpath("btnCancel").click();

	}

	@Test(priority = 3)
	public void validateFormFields() {
		userFormPage();
		getElementByXpath("btnScheduleNow").click();
		Assert.assertEquals(getElementByXpath("emptyUserName").getText(),
				"This field is required.");
		Assert.assertEquals(getElementByXpath("emptyFirstName").getText(),
				"This field is required.");
		Assert.assertEquals(getElementByXpath("emptyLastName").getText(),
				"This field is required.");
		Assert.assertEquals(getElementByXpath("emptyEmail").getText(),
				"This field is required.");
		getElementByXpath("btnCancel").click();
	}

	@Test(priority = 4)
	public void deleteUser() {
		createUser();
		getElementByXpath("checkBoxUser").click();
		if (getElementByXpath("btnDeleteUser").isEnabled()) {
			getElementByXpath("btnDeleteUser").click();
		}
		runJobSupervisor();
	}

	@Test(priority = 5)
	public void deleteAllUsers() throws Exception {
		getElementByXpath("btnUser").click();
		Utils.threadSleep(10000);
		getElementByXpath("checkBoxAllUsers").click();
		if (getElementByXpath("btnDeleteUser").isEnabled()) {
			getElementByXpath("btnDeleteUser").click();
		}
		runJobSupervisor();
	}

}
