package com.suite.regression;

/*
 * Author Bhanu
 */

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.utils.Constants;
import com.utils.ReadWriteExcel;
import com.utils.Utils;

public class Users extends RegressionSuiteBase {

	public void userFormPage() {
		getElementByXpath("btnUser").click();
		getElementByXpath("btnAddDropDown").click();
		getElementByXpath("btnAddUser").click();
	}
	
	@DataProvider
	public String[][] testData() throws IOException{
		ReadWriteExcel.openExcel(Constants.EXCELPATH, "Users");
		String[][] input = ReadWriteExcel.readInput();
		return input;
	}

	@Test(priority = 1, dataProvider = "testData")
	public void createUser(String testEmail, String testPhone, String country) {
		userFormPage();
		getElementByXpath("textUsersName").sendKeys(
				"testuser" + Utils.randGen());
		getElementByXpath("textFirstName").sendKeys("test" + Utils.randGen());
		getElementByXpath("textLastName").sendKeys("user" + Utils.randGen());
		getElementByXpath("textPhone").sendKeys(testPhone);
		getElementByXpath("textEmail").sendKeys(testEmail);
		dropDownList(getElementById("dropDownSelect"), country);
		getElementByXpath("btnScheduleNow").click();
	}

	@Test(priority = 2)
	public void cancelCreatingUser() throws IOException {
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
