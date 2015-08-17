package com.utils;

public class ExcelInterface {
	
	public static boolean checkToRunUtility(ReadWriteExcel xlsx, String sheetName, String colName, String testSuite){
		boolean flag = false;
		if(xlsx.retrieveRunFlag(sheetName, colName, testSuite).equalsIgnoreCase("y")){
			flag = true;
		}else{
			flag = false;
		}
		return flag;
	}
	
	public static String[] checkToRunUtilityOfData(ReadWriteExcel xlsx, String sheetName, String colName){
		return xlsx.retrieveTestDataFlag(sheetName, colName);
	}
	
	public static String[][] getTestData(ReadWriteExcel xlsx, String sheetName){
		return xlsx.retrieveTestData(sheetName);
	}
	
	public static boolean writeTestCaseResult(ReadWriteExcel xlsx, String sheetName, String colName, String rowName, String result){
		return xlsx.writeCaseResult(sheetName, colName, rowName, result);
	}
	
	public static boolean writeTestSuiteResult(ReadWriteExcel xlsx, String sheetName, String colName, String rowName, String result){
		return xlsx.writeCaseResult(sheetName, colName, rowName, result);
	}

}
