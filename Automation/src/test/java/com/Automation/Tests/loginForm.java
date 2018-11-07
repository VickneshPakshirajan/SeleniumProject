package com.Automation.Tests;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

@DataMapping(ExcelFilePath="data\\login.xlsx", ExcelWorksheetName="login")

public class loginForm {
	@JsonProperty
	public String TestScenario;
	public String URL;
	
	@JsonGetter
	public String getTestScenario() {
	return TestScenario;
	
}
	public void setTestScenario(String TestScenario) {
		this.TestScenario = TestScenario;
	}
	
	@JsonGetter
	public String getURL() {
	return URL;
	
}
	public void setURL(String URL) {
		this.URL = URL;
	}

}
