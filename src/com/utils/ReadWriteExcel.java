package com.utils;

/*
 * Author Bhanu
 */

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadWriteExcel {
	private static XSSFWorkbook wb;
	private static XSSFSheet sheet;
	private static XSSFCell cell;
	private static XSSFRow row;
	
	public static void openExcel(String path, String sheetName) throws IOException{
		FileInputStream fin = new FileInputStream(path);
		wb = new XSSFWorkbook(fin);
		int sheetIndex = wb.getSheetIndex(sheetName);
		sheet = wb.getSheetAt(sheetIndex);
	}
	
	public static String[][] readInput(){
		int rowCount = sheet.getLastRowNum()+1;
		int colCount = sheet.getRow(0).getLastCellNum();
		String[][] data = new String[rowCount-1][colCount];
		
		for(int i = 0; i < rowCount-1; i++){
			row = sheet.getRow(i+1);
			for(int j = 0; j < colCount; j++){
				cell = row.getCell(j);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String value = cellConverter(cell);
				data[i][j] = value;
			}
		}
		return data;
	}
	
	public static String cellConverter(XSSFCell cell){
		int type;
		Object result;
		type = cell.getCellType();
		
		switch (type){
		case 0:
			result = cell.getNumericCellValue();
			break;
		 
		case 1:
			result = cell.getStringCellValue();
			break;
			
		default:
			throw new RuntimeException("Unformatted cell");
		}
		return result.toString();
	}
}
