package com.Automation.Tests;

import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.Automation.Categories.IDevelopment;
import com.Automation.Categories.IRegressionTest;

public class CategoryTests {
	
		
	WrapperMethods wM;

		
	@Before 
	public void createDriver()
	{
	
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
	
	@Test
	@Category({IDevelopment.class, IRegressionTest.class})
	public void ShouldBeAbleToOpenGoogle()
	{
		//driver.get("http://www.google.com");
		wM = new WrapperMethods("TestCase1");
	
		wM.lBrwsr("Chrome", "http://www.google.com");
		wM.vrfyttl("Google");
		
	}
	
	@After
	public void quitBrowser()
	{
		wM.quitDriver();
		//driver.quit();
		
	}	

}
