package com.Automation.Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.Automation.Categories.IDevelopment;
import com.Automation.Categories.IRegressionTest;

public class CategoryTests {
	WebDriver driver;
	
	@Before
	public void createDriver()
	{
		
		//C:\Users\USER\git\SeleniumProject1\Automation\drivers
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\USER\\git\\SeleniumProject\\Automation\\drivers\\chromedriver.exe");	 
		// Initialize browser
		driver=new ChromeDriver();		 
		driver.manage().window().maximize();
	}
	
	@Test
	@Category({IDevelopment.class, IRegressionTest.class})
	public void ShouldBeAbleToOpenFacebook()
	{
		driver.get("http://www.facebook.com");
	}
	
	@Test
	@Category({IRegressionTest.class})
	public void ShouldBeAbleToOpenGoogle()
	{
		driver.get("http://www.google.com");
	}
	
	@After
	public void quitBrowser()
	{
		driver.quit();
	}	

}
