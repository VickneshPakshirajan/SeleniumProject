package com.Automation.Tests;

public final class AnnotationHandle {
	
	public <T> String ExcelWorksheetName(Class<T> objClass)
	{
		if(objClass.isAnnotationPresent(DataMapping.class)) {
			DataMapping dataMapping = objClass.getAnnotation(DataMapping.class);
			return dataMapping.ExcelWorksheetName();
		}
		return null;
	}
	
	public <T> String ExcelFilePath(Class<T> objClass)
	{
		if(objClass.isAnnotationPresent(DataMapping.class)) {
			DataMapping dataMapping = objClass.getAnnotation(DataMapping.class);
			return dataMapping.ExcelFilePath();
		}
		return null;
	}

}
