package com.suite.regression;

/*
 * Author Bhanu
 */

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.utils.Constants;
import com.utils.ReadWriteExcel;
import com.utils.Utils;

public class AppContent extends RegressionSuiteBase {
	
	@DataProvider
	public String[][] testData() throws IOException{
		ReadWriteExcel.openExcel(Constants.EXCELPATH, "AppContent");
		String[][] input = ReadWriteExcel.readInput();
		return input;
	}
	
	@Test(priority=1, dataProvider="testData")
	public void uploadFile(String execPath) throws Exception {
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
			Runtime.getRuntime().exec(execPath);
		} catch (IOException ie) {
			System.out.println("Executable file not found " + ie);
		}
		Utils.threadSleep(15000);
		getElementByXpath("textVersion").sendKeys("1.0");
		getElementByXpath("btnAddToQueue").click();
		Utils.threadSleep(5000);
		runJobSupervisor();
	}
	
	@Test(priority=2)
	public void deleteFile() {
		getElementByXpath("btnAppContent").click();
		getElementByXpath("checkBoxDeleteFile").click();
		if(getElementByXpath("btnDelete").isEnabled()){
			getElementByXpath("btnDelete").click();
		}
		runJobSupervisor();
	}
	
	@Test(priority=3)
	public void deleteAllFile() throws Exception{
		getElementByXpath("btnAppContent").click();
		Utils.threadSleep(10000);
		getElementById("checkBoxDeleteAllFiles").click();
		if(getElementByXpath("btnDelete").isEnabled()){
			getElementByXpath("btnDelete").click();
		}
		runJobSupervisor();
	}

}
