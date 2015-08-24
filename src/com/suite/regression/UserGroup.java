package com.suite.regression;

/*
 * Author Bhanu
 */

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.utils.Utils;

public class UserGroup extends RegressionSuiteBase {
	WebElement source, destination, userSearchResult;

	@Test(priority = 1)
	public void createEmptyUserGroup() {
		getElementByXpath("btnUserGroup").click();
		getElementByXpath("btnCreateUG").click();
		getElementByXpath("textUGName").sendKeys("UserGroup" + Utils.randGen());
		getElementByXpath("btnScheduleNow").click();
	}

	@Test(priority = 2)
	public void addDefaultUser() {
		getElementByXpath("btnUserGroup").click();
		getElementByXpath("btnCreateUG").click();
		getElementByXpath("textUGName").sendKeys("UserGroup" + Utils.randGen());
		source = getElementByXpath("defaultUserSource");
		destination = getElementByXpath("userDestination");
		dragAndDrop(source, destination);
		getElementByXpath("btnScheduleNow").click();
	}

	@Test(priority = 3)
	public void addCustomUser() {
		getElementByXpath("btnUserGroup").click();
		getElementByXpath("btnCreateUG").click();
		getElementByXpath("textUGName").sendKeys("UserGroup" + Utils.randGen());
		source = getElementByXpath("customUserSource");
		destination = getElementByXpath("userDestination");
		dragAndDrop(source, destination);
		getElementByXpath("btnScheduleNow").click();
	}

	@Test(priority = 4)
	public void addUserBySearch() {
		getElementByXpath("btnUserGroup").click();
		getElementByXpath("btnCreateUG").click();
		getElementByXpath("textUGName").sendKeys("UserGroup" + Utils.randGen());
		getElementByXpath("userSearchField").sendKeys("fep", Keys.ENTER);

		int attempt = 0;
		while (attempt < 2) {
			source = getElementByXpath("searchedUserSource");
			destination = getElementByXpath("userDestination");
			dragAndDrop(source, destination);
			attempt++;
		}
		getElementByXpath("btnScheduleNow").click();
	}

	@Test(priority = 5)
	public void validateFormField() {
		getElementByXpath("btnUserGroup").click();
		getElementByXpath("btnCreateUG").click();
		getElementByXpath("btnScheduleNow").click();
		Assert.assertEquals(getElementByXpath("emptyUGName").getText(),
				"This field is required.");
		getElementByXpath("btnCancel").click();
	}

	@Test(priority = 6)
	public void cancelCreatingUserGroup() {
		getElementByXpath("btnUserGroup").click();
		getElementByXpath("btnCreateUG").click();
		getElementByXpath("textUGName").sendKeys("UserGroup" + Utils.randGen());
		source = getElementByXpath("defaultUserSource");
		destination = getElementByXpath("userDestination");
		dragAndDrop(source, destination);
		getElementByXpath("btnCancel").click();
	}

	@Test(priority = 7)
	public void deleteUserGroup() {
		getElementByXpath("checkBoxUserGroup").click();
		if (getElementByXpath("btnDeleteUserGroup").isEnabled()) {
			getElementByXpath("btnDeleteUserGroup").click();
		}
		runJobSupervisor();
	}

	@Test(priority = 8)
	public void deleteAllUserGroup() throws Exception {
		getElementByXpath("btnUserGroup").click();
		Utils.threadSleep(5000);
		getElementByXpath("checkBoxAllUserGroup").click();
		if (getElementByXpath("btnDeleteUserGroup").isEnabled()) {
			getElementByXpath("btnDeleteUserGroup").click();
		}
		runJobSupervisor();
	}

}
