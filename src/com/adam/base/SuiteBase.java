package com.adam.base;

import java.io.FileInputStream;
import java.util.Properties;

import com.utils.ReadWriteExcel;

public class SuiteBase {
	
	public ReadWriteExcel testSuiteList, regressionSuiteTestCases;
	public static Properties OR;
	public FileInputStream fin;
	
	public Properties readOR(String pathOR) throws Exception{
		fin = new FileInputStream(pathOR);
		OR = new Properties(System.getProperties());
		OR.load(fin);
		return OR;
	}
	
	public void init(){
		testSuiteList = new ReadWriteExcel("C:/bhanu_materials/Selenium/ToggleManager/src/com/excel/TestSuiteList.xlsx");
		regressionSuiteTestCases = new ReadWriteExcel("C:/bhanu_materials/Selenium/ToggleManager/src/com/excel/RegressionSuiteTestCases.xlsx");
	}
	
}
