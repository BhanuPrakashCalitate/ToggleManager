package com.suite.regression;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

import com.utils.Constants;
import com.utils.Utils;

public class AppContent extends RegressionSuiteBase {
	public static String autoIt = "./src/com/utils/adamappupload.exe";

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
	
	@Test
	public void uploadFile() throws Exception {
		getElementByXpath("btnAppContent").click();
		upload1 = getElementByXpath("btnUploadNewFile");
		upload2 = getElementByXpath("btnUpload");
		try{
			if(upload1.isDisplayed()){
				upload1.click();
			}
		}catch(Exception e){
			upload2.click();
		}
		getElementByXpath("btnUploadFile").click();
		try {
			Runtime.getRuntime().exec(autoIt);
		} catch (IOException ie) {
			System.out.println("Executable file not found " + ie);
		}
		Utils.threadSleep(10000);
		getElementByXpath("textVersion").sendKeys("1.0");
		getElementByXpath("btnAddToQueue").click();
		Utils.threadSleep(5000);
		runJobSupervisor();
	}

}
