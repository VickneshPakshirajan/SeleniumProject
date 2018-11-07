package com.Automation.Tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExcelSpreadsheetProcessor {
	private static transient Collection<Object[]> data = null;
	private Workbook workbook = null;
	private Sheet sheet = null;
	private String returnJSON = null;
	private String TestScenarioFilter;
	private String TestScenarioHeader = "TestScenario";
	
	//To count number of columns in a sheet
	private int countNonEmptyColumns(final Sheet sheet) {
		int columnCount = 0;
		Row firstRow = sheet.getRow(0);
		
		for(Cell cell : firstRow) {
			if(cell.getCellType() == Cell.CELL_TYPE_BLANK)
				break;
			columnCount++;
		}
		return columnCount;		
	}
	
	//To get the object value from Excel Cell
	private Object objectFrom(final Workbook workbook, final Cell cell) {
		Object cellValue = null;
		if(cell.getCellType() == Cell.CELL_TYPE_STRING)
			cellValue = cell.getRichStringCellValue().toString();
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
			cellValue = getNumericCellValue(cell);
		if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN)
			cellValue = cell.getBooleanCellValue();
		if(cell.getCellType() == Cell.CELL_TYPE_FORMULA)
				cellValue = evaluateCellFormaula(workbook, cell);				
		return cellValue;		
	}
	
	//Getting Numeric value from cell
	private Object getNumericCellValue(final Cell cell) {
		Object cellValue;
		if(DateUtil.isCellDateFormatted(cell))
			cellValue = new Date(cell.getDateCellValue().getTime());
		else
			cellValue = cell.getNumericCellValue();
		return cellValue;
	}
	
	//To get evaluated value in a cell with Formula
	private Object evaluateCellFormaula(final Workbook workbook, final Cell cell) {
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		CellValue cellValue = evaluator.evaluate(cell);
		Object result = null;
		
		if(cellValue.getCellType() == Cell.CELL_TYPE_BOOLEAN)
			result = cellValue.getBooleanValue();
		else if (cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC)
			result = cellValue.getNumberValue();
		else if (cellValue.getCellType() == Cell.CELL_TYPE_STRING)
			result = cellValue.getStringValue();
		
		return result;
	}
	
	//To check TestScenario header present
	private int getColumnOfScenarioFilter(int numberOfColumns){
		int count = -1;
		Row headerRow = sheet.getRow(0);
		for (int column = 0; column < numberOfColumns; column++) {
			Cell cell = headerRow.getCell(column);
			Object cellObject = objectFrom(workbook, cell);
			if(cellObject != null)
				if(cellObject.toString().equals(TestScenarioHeader))
					return column;
		}
		return count;
	}
	
	// To check if a row is empty
	private boolean isRowEmpty(final Row row) {
		Cell firstCell = row.getCell(0);
		return ((firstCell == null) || (firstCell.getCellType() == Cell.CELL_TYPE_BLANK));		
	}
	
	
	public <T> T ExcelDataToClassObject(Class<T> objClass, String scenarioFilter) throws EncryptedDocumentException, InvalidFormatException, IOException {
		
		AnnotationHandle annotationHandle = new AnnotationHandle();
		FileInputStream excelInputStream = new FileInputStream(new File(annotationHandle.ExcelFilePath(objClass)));
		workbook = WorkbookFactory.create(excelInputStream);
		sheet = workbook.getSheet(annotationHandle.ExcelWorksheetName(objClass));
		loadFromSpreadsheet(scenarioFilter);
		return new ObjectMapper().readerFor(objClass).readValue(returnJSON);		
	}
	
	private void loadFromSpreadsheet(String scenarioFilter) {
		try {
			int numberOfColumns = countNonEmptyColumns(sheet);
			int testScenarioColumnCount = getColumnOfScenarioFilter(numberOfColumns);
			if(testScenarioColumnCount < 0) {
				//TODO:: Add Log info
				return;
			}
			Row HeaderRow = null;
			for(Row row : sheet) {
				if(isRowEmpty(row))
					break;
				else {
					if(row.getRowNum()!=0) {
						if(!row.getCell(testScenarioColumnCount).getRichStringCellValue().toString().equals(scenarioFilter))
							continue;
						
						JSONObject json = new JSONObject();
						
						for (int column=0; column < numberOfColumns; column++) {
							String strCellValue = null;
							String HeaderCell = HeaderRow.getCell(column).getRichStringCellValue().toString();
							Cell cell = row.getCell(column);
							if(cell == null)
								strCellValue = "";
							else {
								Object cellObject = objectFrom(workbook, cell);
								if(cellObject == null)
									strCellValue = "";
								else {
									strCellValue = cellObject.toString();
									//TODO: Add Key word Operations
								}
							}
							json.put(HeaderCell, strCellValue);
						}
						returnJSON = json.toJSONString();
						break;						
					}
					else
						HeaderRow = row;
				}
			}
		} catch (Exception e){
			//TODO : Handle Exception
		}
		
	}

}
