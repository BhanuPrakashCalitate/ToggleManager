package com.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadWriteExcel {
	
	public String filePath;
	public FileInputStream fin;
	public FileOutputStream fout;
	private Workbook wb;
	private Sheet ws;
	
	public ReadWriteExcel(String filePath){
		this.filePath = filePath;
		try{
			fin = new FileInputStream(filePath);
			wb= new XSSFWorkbook(fin);
			ws = wb.getSheetAt(0);
			fin.close();
		}catch(IOException ie){
			ie.printStackTrace();
		}
	}
	
	//To retrieve number of rows from xlsx sheet
	public int retrieveRowCount(String sheetName){
		int sheetIndex = wb.getSheetIndex(sheetName);
		
		if(sheetIndex == -1){
			return 0;
		}else{
			ws = wb.getSheetAt(sheetIndex);
			int rowCount = ws.getLastRowNum() + 1;
			return rowCount;
		}
	}
	
	//To retrieve number columns from xlsx sheet
	public int retrieveColCount(String sheetName){
		int sheetIndex = wb.getSheetIndex(sheetName);
		
		if(sheetIndex == -1){
			return 0;
		}else{
			ws = wb.getSheetAt(sheetIndex);
			int colCount = ws.getRow(0).getLastCellNum();
			return colCount;
		}
	}
	
	//To retrieve flag of test suite or test case which has to be run
	public String retrieveRunFlag(String sheetName, String colName, String rowName){
		int sheetIndex = wb.getSheetIndex(sheetName);
		
		if(sheetIndex == -1){
			return " ";
		}
		
		int rowNum = retrieveRowCount(sheetName);
		int colNum = retrieveColCount(sheetName);
		int colNumber = -1;
		int rowNumber = -1;
		
		Row suiteRow = ws.getRow(0);
		
		for(int i = 0; i < colNum; i++){
			if(suiteRow.getCell(i).getStringCellValue().equals(colName.trim())){
				colNumber = i;
			}
		}
		
		if(colNumber == -1){
			return " ";
		}
		
		for(int j = 0; j < rowNum; j++){
			Row suiteCol = ws.getRow(j);
			if(suiteCol.getCell(0).getStringCellValue().equals(rowName.trim())){
				rowNumber = j;
			}
		}
		
		if(rowNumber == -1){
			return " ";
		}
		
		Row row = ws.getRow(rowNumber);
		Cell cell = row.getCell(colNumber);
		if(cell == null){
			return " ";
		}
		
		String value = cellToString(cell);
		return value;
	}
	
	//To get type of cell value
	public static String cellToString(Cell cell){
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
			
		default :
			throw new RuntimeException("Unformatted cell");
		}
		
		return result.toString();
	}
	
	//To retrieve test data flag
	public String[] retrieveTestDataFlag(String sheetName, String colName){
		int sheetIndex = wb.getSheetIndex(sheetName);
		
		if(sheetIndex == -1){
			return null;
		}else{
			int rowNum = retrieveRowCount(sheetName);
			int colNum = retrieveColCount(sheetName);
			int colNumber = -1;
			
			Row suiteRow = ws.getRow(0);
			String data[] = new String[rowNum - 1];
			for(int i = 0; i < colNum; i++){
				if(suiteRow.getCell(i).getStringCellValue().equals(colName.trim())){
					colNumber  = i;
				}
			}
			
			if(colNumber == -1){
				return null;
			}
			
			for(int j = 0; j < rowNum-1; j++){
				Row row = ws.getRow(j+1);
				if(row == null){
					data[j] = " ";
				}else{
					Cell cell = row.getCell(colNumber);
					if(cell == null){
						data[j] = " ";
					}else{
						String value = cellToString(cell);
						data[j] = value;
					}
				}
			}
			
			return data;
		}
	}
	
	//to retrieve test data
	public Object[][] retrieveTestData(String sheetName){
		int sheetIndex = wb.getSheetIndex(sheetName);
		
		if(sheetIndex == -1){
			return null;
		}else{
			int rowNum = retrieveRowCount(sheetName);
			int colNum = retrieveColCount(sheetName);
			
			Object data[][] = new Object[rowNum-1][colNum-2];
			
			for(int i = 0; i < rowNum-1; i++){
				Row row = ws.getRow(i+1);
				for(int j = 0; j < colNum-2; j++){
					if(row == null){
						data[i][j] = " ";
					}else{
						Cell cell = row.getCell(j);
						if(cell == null){
							data[i][j] = " ";
						}else{
							cell.setCellType(Cell.CELL_TYPE_STRING);
							String value = cellToString(cell);
							data[i][j] = value;
						}
					}
				}
			}
			
			return data;
		}
	}
	
	//To write result in test case and test data list sheet
	public boolean writeCaseResult(String sheetName, String colName, String rowName, String result){
		try{
		int sheetIndex = wb.getSheetIndex(sheetName);
		
		if(sheetIndex == -1){
			return false;
		}
		
		int rowNum = retrieveRowCount(sheetName);
		int colNum = retrieveColCount(sheetName);
		int rowNumber = -1;
		int colNumber = -1;
		
		Row suiteRow = ws.getRow(0);
		for(int i = 0; i < colNum; i++){
			if(suiteRow.getCell(i).getStringCellValue().equals(colName.trim())){
				colNumber = i;
			}
		}
		
		if(colNumber == -1){
			return false;
		}
		
		for(int j = 0; j < rowNum-1; j++){
			Row row1 = ws.getRow(j+1);
			Cell cell = row1.getCell(0);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String value = cellToString(cell);
			if(value.equals(rowName)){
				rowNumber = j+1;
				break;
			}
		}
		
		Row row = ws.getRow(rowNumber);
		Cell cell = row.getCell(colNumber);
		if(cell == null){
			cell = row.createCell(colNumber);
		}
		cell.setCellValue(result);
		
		fout = new FileOutputStream(filePath);
		wb.write(fout);
		fout.close();
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//To write result in test suite list sheet
	public boolean writeSuiteResult(String sheetName, String colName, String rowName, String result){
		try{
			int sheetIndex = wb.getSheetIndex(sheetName);
			if(sheetIndex == -1){
				return false;
			}
			int colNum = retrieveColCount(sheetName);
			int rowNum = retrieveRowCount(sheetName);
			int rowNumber = -1;
			int colNumber = -1;
			
			Row suiteRow = ws.getRow(0);
			for(int i = 0; i < colNum; i++){
				if(suiteRow.getCell(i).getStringCellValue().equals(colName.trim())){
					colNumber = i;
				}
			}
			
			if(colNumber == -1){
				return false;
			}
			
			for(int j = 0; j < rowNum-1; j++){
				Row row1 = ws.getRow(j+1);
				Cell cell = row1.getCell(0);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String value = cellToString(cell);
				if(value.equals(rowName)){
					rowNumber = j+1;
					break;
				}
			}
			
			Row row = ws.getRow(rowNumber);
			Cell cell = row.getCell(colNumber);
			if(cell == null){
				cell = row.createCell(colNumber);
			}
			cell.setCellValue(result);
			
			fout = new FileOutputStream(filePath);
			wb.write(fout);
			fout.close();
		}catch(Exception e ){
			e.printStackTrace();
			
			return false;
		}
		return true;
	}

}
