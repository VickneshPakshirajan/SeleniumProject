package com.Automation.Tests;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataMapping {
	String ExcelWorksheetName();
	String ExcelFilePath();
}
