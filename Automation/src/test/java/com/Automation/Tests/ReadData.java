package com.Automation.Tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadData {
	public static ArrayList<ArrayList<String>> readingFromExcel(String workBookName) throws IOException
	{
		try {
			// Step 1: Open the file
			FileInputStream file=new FileInputStream(new File("data\\"+workBookName+".xlsx"));
			//FileInputStream file=new FileInputStream(new File("C:\\Users\\USER\\git\\SeleniumProject\\Automation\\data\\"+workBookName+".xlsx"));
			// Open the workbook from the source
			XSSFWorkbook workbook= new XSSFWorkbook(file);
			
			// Go to the sheet 1
			XSSFSheet sheet=workbook.getSheetAt(0);
			
			// Get the row count
			int rowCnt =sheet.getLastRowNum();
			
			// Get the column count of row 0
			int colCnt = sheet.getRow(0).getLastCellNum();
			
			ArrayList<ArrayList<String>> records = new ArrayList<ArrayList<String>>(); 
					
			// Loop through all rows
			for(int r=1; r <= rowCnt ; r++){
				 
				// set to the specific row
				XSSFRow row = sheet.getRow(r);
				
				ArrayList<String> record = new ArrayList<String>();
				
				// Each row, loop through all columns
				for(int c=0; c<colCnt; c++){
					//System.out.println(row.getCell(c).getStringCellValue());
					record.add(row.getCell(c).getStringCellValue());
					
				}
				
				records.add(record);
			}
			
			
			return records;
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("File Name Not Matching");
		}
		return null;
		
		
	}
/*
public static void main(String args[]) throws IOException{
		ArrayList<ArrayList<String>> data = readingFromExcel("Registration");
		System.out.println(data.get(0).get(0));
		System.out.println(data.get(1).get(1));
		
	}
*/
}
