package com.Automation.Tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReporter {
	FileInputStream fis;
	XSSFWorkbook wrkbk;
	XSSFSheet sht;
	FileOutputStream fos;
	String testcase;
	
	public ExcelReporter(String testcaseName){
		System.out.println("am in" +testcaseName);
		this.testcase = testcaseName;
		createHeader();
		
	}
	
	public void createHeader() {
		try {
			wrkbk = new XSSFWorkbook();
			sht = wrkbk.createSheet("report");
			XSSFRow row = sht.createRow(0);
			row.createCell(0).setCellValue("Step No");
			row.createCell(1).setCellValue("Step Description");
			row.createCell(2).setCellValue("Status");
			flushWorkBk();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		}
	
	
	public void reportStep(String description, String status)  {
		try {
			System.out.println("My testcase Name is "+testcase);
			FileInputStream fs = new FileInputStream(new File("reports\\"+ testcase + "_report.xlsx"));
			wrkbk = new XSSFWorkbook(fs);
			sht = wrkbk.getSheetAt(0);
			int j = sht.getLastRowNum() + 1;
			XSSFRow row = sht.createRow(j);
			row.createCell(0).setCellValue(j - 1);
			row.createCell(1).setCellValue(description);
			row.createCell(2).setCellValue(status);
			flushWorkBk();
		} catch (Exception e) {
			e.printStackTrace();
		}

		}
	
	public void  flushWorkBk() {
		
		try {
			fos = new FileOutputStream(new File("reports\\"+testcase+"_report.xlsx"));
			wrkbk.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}

