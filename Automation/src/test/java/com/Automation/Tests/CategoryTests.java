package com.Automation.Tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hpsf.ReadingNotSupportedException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.Automation.Categories.IDevelopment;
import com.Automation.Categories.IRegressionTest;

import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Title;

public class CategoryTests {
		
	WrapperMethods wM;
	ExcelSpreadsheetProcessor exp = new ExcelSpreadsheetProcessor();
	
	Connection conn = null;
	 
	// Object of Statement. It is used to create a Statement to execute the query
	Statement stmt = null;
 
	//Object of ResultSet => 'It maintains a cursor that points to the current row in the result set'
	ResultSet resultSet = null;
		
	WebDriver driver;
	
	@Before 
	public void createDriver() throws ClassNotFoundException, SQLException
	{
				// Register JDBC driver (JDBC driver name and Database URL)
				Class.forName("com.mysql.jdbc.Driver");
				
				System.out.println("Driver loaded");
				
				// Open a connection
				// Connection conn = DriverManager.getConnection("jdbc:mysql://localized", user, password)
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/selenium", "root", "ROOT");
				System.out.println("Connection loaded");
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from seleniumusers");
				
				while(rs.next())
					
				{
					String firstname = rs.getString("firstname");
					System.out.println("Fistname is " +firstname);
					
				}
				
		//C:\Users\USER\git\SeleniumProject1\Automation\drivers
		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\USER\\git\\SeleniumProject\\Automation\\drivers\\chromedriver.exe");	 
		// Initialize browser
		/*driver=new ChromeDriver();		 
		driver.manage().window().maximize();*/
		
		
	}
	
	@Test
	@Category({IRegressionTest.class})
	public void ShouldBeAbleToOpenFacebook()
	{
		
		//driver.get("http://www.facebook.com");
		
	}
	@Title("Title check")
    @Description("Checking the title of the loaded page.")
	@Test
	@Category({IDevelopment.class, IRegressionTest.class})
	public void ShouldBeAbleToOpenGoogle() throws IOException, EncryptedDocumentException, InvalidFormatException
	{
		String testcaseName = "GoogleLogin";
		wM = new WrapperMethods(testcaseName);
		
			/*ArrayList<ArrayList<String>> data = ReadData.readingFromExcel("login");
				int rowCount = data.size();	  
		  		for (int i = 0; i <= rowCount; i++) {
			  	String URL = data.get(i).get(1);
			  	wM.lBrwsr("Chrome", URL);
		  }*/
		  		
		loginForm subinfoForm =exp.ExcelDataToClassObject(loginForm.class, testcaseName);
		//loginForm subinfoForm =exp.ExcelDataToClassObject(loginForm.class, "FaceBookLogin"); 
		wM.lBrwsr("Chrome",subinfoForm.getURL());
		//wM.lBrwsr("Chrome", "http://www.google.com");
		AllureLogUtil.log("Title Loaded: "+subinfoForm.getURL());
		wM.vrfyttl("Google");
				
	}
	
	@After
	public void quitBrowser()
	{
		wM.quitDriver();
		//driver.quit();
		
	}

}
