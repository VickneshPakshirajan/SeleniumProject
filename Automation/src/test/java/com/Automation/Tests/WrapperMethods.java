package com.Automation.Tests;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.Select;

import ru.yandex.qatools.allure.annotations.Step;


public class WrapperMethods {

	
	ExcelReporter er;
	int i = 0;
	
	WebDriver driver;
	
	public WrapperMethods(String testcaseName){
		System.out.println(testcaseName);
		er =new ExcelReporter(testcaseName);
	}
	
	public void launchBrwsr1(String dr1, String URL) throws MalformedURLException {
		
		try{
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setBrowserName(dr1);
				dc.setPlatform(Platform.WINDOWS);
				
				driver = new RemoteWebDriver(new URL("http://192.168.1.27:4444/wd/hub"), dc);
				//Maximize the firefox
				driver.manage().window().maximize();
				
				//Load the url
				driver.navigate().to(URL);
				
				//Implicit wait
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} 
	catch (UnreachableBrowserException e) {
		// Print the below message when user is unable to launch the browser or URL is wrong
		System.out.println("Unable to launch the browser or URL entered is wrong");
		er.reportStep("Unable to launch ", "Failed");
		}
	catch (Exception e) {
		e.printStackTrace();
		}
	finally {
		// However it is take the snapshot
		takeSnap();
		}
	}
	public void lBrwsr(String dr, String URL) {
		try {
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\USER\\git\\SeleniumProject\\Automation\\drivers\\chromedriver.exe");	 
			//Launch Google Chrome
			driver=new ChromeDriver();		 
			
			er.reportStep("Chrome Successfully launched", "Passed");
			//Maximize the Browser
			driver.manage().window().maximize();
			//Load the url
			driver.navigate().to(URL);
			//Implicit wait
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to launch the browser or URL entered is wrong");
			er.reportStep("Unable to launch ", "Failed");
		}
		finally {
			// However it is take the snapshot
			takeSnap();
			}
	}
	/*
	public void launchBrwsr(String dr1, String URL) {
	
	try {
		if(dr1.equalsIgnoreCase("IE"))
		{		
			//Launch Internet Explorer
			System.setProperty("webdriver.ie.driver","drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			er.reportStep("IE Successfully launched", "Passed");
			} 
		else if(dr1.equalsIgnoreCase("Chrome"))	
			{
			//Launch Google Chrome
			System.setProperty("webdriver.chrome.driver","drivers\\chromeDriver.exe");
			driver = new ChromeDriver();
			er.reportStep("Chrome Successfully launched", "Passed");
			} 
		else	
			{
			// Launching the firefox
			driver = new FirefoxDriver();
			er.reportStep("FireFox Successfully launched", "Passed");
		}
		
		//Maximize the firefox
		driver.manage().window().maximize();
		
		//Load the url
		driver.navigate().to(URL);
		
		//Implicit wait
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} 
	catch (UnreachableBrowserException e) {
		// Print the below message when user is unable to launch the browser or URL is wrong
		System.out.println("Unable to launch the browser or URL entered is wrong");
		er.reportStep("Unable to launch ", "Failed");
		}
	finally {
		// However it is take the snapshot
		takeSnap();
		}
	} */
	
	public void close()
	{
	//Closing the firefox
	driver.close();
	er.reportStep("Broswser Closed","Passed");
	}
	
	public void quitDriver()
	{
		driver.close();
		er.reportStep("Broswser Quited","Passed");
	}

	public void takeSnap() {
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File("reports\\snaps\\snap" + i	+ ".png"));
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
		i++;
		}
	}
	
	public void clckurl(String clckurl1, String valclckurl1)
	{
		try {
			//sending the text to the alert
			WebElement url1 = driver.findElement(By.linkText(clckurl1));
			String valurl = url1.getAttribute(valclckurl1);
			System.out.println("URL of the : " + clckurl1 + "is :" + valurl);
			driver.navigate().to(valurl);
			er.reportStep("URL is correct", "Passed");
		} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The link text " + clckurl1 + "and its value URL :" + valclckurl1 + " is not found");
			er.reportStep("NoSuchElementException", "Failed");
			} 
	/*	catch (SessionNotFoundException f) {
			// If user is displayed with the session not found error
			System.out.println("Please check your internet connection or check the URL what you are entered");
			er.reportStep("SessionNotFoundException", "Failed");
			}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}
	
	public void enterValbyid(String id, String idval)
	{
		try {
			//Enter the value in text field by using the locator - id.
			driver.findElement(By.id(id)).clear();
			driver.findElement(By.id(id)).sendKeys(idval);
			er.reportStep("Values entered by ID", "Passed");
			} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The element" + id + "and their value " + idval + " is not found");
			er.reportStep("NoSuchElementException so Values not entered by ID", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}
	
	public void enterValbyname(String nameid, String nameval)
	{
		try {
			//Enter the value in text field by using the locator - name.
			driver.findElement(By.name(nameid)).clear();
			driver.findElement(By.name(nameid)).sendKeys(nameval);	
			er.reportStep("Values entered by Name", "Passed");
			} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The element" + nameid + "and their value " + nameval + " is not found");
			er.reportStep("NoSuchElementException so Values not entered by Name", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}

	public void enterValbyclsnme(String clssid, String clssval)
	{
		try {
			//Enter the value in text field by using the locator - classname.
			driver.findElement(By.id(clssid)).clear();
			driver.findElement(By.className(clssid)).sendKeys(clssval);
			er.reportStep("Values entered by ClassName", "Passed");
			} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The element" + clssid + "and their value " + clssval + " is not found");
			er.reportStep("NoSuchElementException so Values not entered by ClassName", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}
	
	public void enterValbytagnme(String tagid, String tagval)
	{
		try {
			//Enter the value in text field by using the locator - tagname.
			driver.findElement(By.id(tagid)).clear();
			driver.findElement(By.tagName(tagid)).sendKeys(tagval);
			er.reportStep("Values entered by TagName", "Passed");
			} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The element" + tagid + "and their value " + tagval + " is not found");
			er.reportStep("NoSuchElementException so Values not entered by TagName", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}

	public void enterValbyxpth(String xpthid, String xpthval)
	{
		try {
			//Enter the value in text field by using the locator - xpath.
			driver.findElement(By.id(xpthid)).clear();
			driver.findElement(By.xpath(xpthid)).sendKeys(xpthval);
			er.reportStep("Values entered by Xpath", "Passed");
			} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The element" + xpthid + "and their value " + xpthval + " is not found");
			er.reportStep("NoSuchElementException so Values not entered by Xpath", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}
	
	public void enterValbylnktxt(String lnktxtid, String lnktxtval)
	{
		try {
			//Enter the value in text field by using the locator - link text.
			driver.findElement(By.id(lnktxtid)).clear();
			driver.findElement(By.linkText(lnktxtid)).sendKeys(lnktxtval);
			er.reportStep("Values entered by Linktext", "Passed");
			
			} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The element" + lnktxtid + "and their value " + lnktxtval + " is not found");
			er.reportStep("NoSuchElementException so Values not entered by Linktext", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}
	
	public void clickByid(String clkid)
	{
		try {
			//Click the button/link in the application by using the locator - id.
			driver.findElement(By.id(clkid)).click();
			er.reportStep("Values Clicked", "Passed");
			} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The element" + clkid + " is not found to click");
			er.reportStep("Values not Clicked", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}
		
	public void clckbyXpath(String clckxpth)
	{
		try {
			//Click the button/link in the application by using the locator - xpath.
			driver.findElement(By.xpath(clckxpth)).click();
			er.reportStep("Values Clicked", "Passed");
			} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The element" + clckxpth + " is not found to click");
			er.reportStep("Values not Clicked", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}
	
	public void clckbylnktxt(String clcklnktxt)
	{
		try {
			//Click the button/link in the application by using the locator - Link text.
			driver.findElement(By.id(clcklnktxt)).click();
			er.reportStep("Values Clicked", "Passed");
			} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The element" + clcklnktxt + " is not found to click");
			er.reportStep("Values not Clicked", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}
	
	public void clckbynme(String clcknme)
	{
		try {
			//Click the button/link in the application by using the locator - name.
			driver.findElement(By.id(clcknme)).click();
			er.reportStep("Values Clicked", "Passed");
			} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The element" + clcknme + " is not found to click");
			er.reportStep("Values not Clicked", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}
	
	public void clckbyclsnme(String clckclsnme)
	{
		try {
			//Click the button/link in the application by using the locator - classname.
			driver.findElement(By.id(clckclsnme)).click();
			er.reportStep("Values Clicked", "Passed");
			} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The element" + clckclsnme + " is not found to click");
			er.reportStep("Values not Clicked", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}
	
	public void clckbytagnme(String clcktagnme)
	{
		try {
			//Click the button/link in the application by using the locator - tagname.
			driver.findElement(By.id(clcktagnme)).click();
			er.reportStep("Values Clicked", "Passed");
			} 
		catch (NoSuchElementException e) {
			// if the particular element is not found print the below line
			System.out.println("The element" + clcktagnme + " is not found to click");
			er.reportStep("Values not Clicked", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
		}		}

	public void vrfyTxtid (String vrfyid, String valvrfyid)
	{
		try {
			//verifying the text in the application using contains & gettext().
			String strvrfytxtid = driver.findElement(By.id(vrfyid)).getText();
			System.out.println("The current Title is : " + strvrfytxtid);
			if (strvrfytxtid.contains(valvrfyid)){
			System.out.println("Good, Text is verified sucessfully");
			er.reportStep("Text is verified sucessfully", "Passed");
			}
			else
			{
			System.out.println("Ooops! Text doesn't match....");
			er.reportStep("Text doesnt match", "Failed");
			}
			} 
		catch (NoSuchElementException f) {
			// if the particular element is not found print the below line
			System.out.println("The content :" + valvrfyid + " is not found");
			er.reportStep("Failed due to NoSuchElementException", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception 
			takeSnap();
			}
	}
	@Step("Verify Tile")
	public void vrfyttl (String valttl)
	{
		try {
			//verify the title of the window  or application using contains & gettitle().
			String vrfyttl = driver.getTitle();
			System.out.println("The current Title is : " + vrfyttl);
			if (vrfyttl.contains(valttl))
			{
			System.out.println("Title Matches sucessfully");
			er.reportStep("Title Matches sucessfully", "Passed");
			AllureLogUtil.log("Test Passed. Expected: Google | Actual: "+vrfyttl);
			}
			else
			{
				System.out.println("Ooops! Title doesn't Match....");
				er.reportStep("Title doesn't Match", "Failed");
			}
			}
		catch (NoSuchElementException f) {
			// if the particular element is not found print the below line
			System.out.println("The content : " + valttl + " is not found");
			er.reportStep("Failed due to NoSuchElementException", "Failed");
			} 
		/*	catch (SessionNotFoundException f) {
		// If user is displayed with the session not found error
		System.out.println("Please check your internet connection or check the URL what you are entered");
		er.reportStep("SessionNotFoundException", "Failed");
		}*/
		finally {
			// It should be executed either if we found too many error or even if we didn't found any exception
			takeSnap();
			}
	}
	
	public void mouseoverusingclassname(String val){
		WebElement mouseover =  driver.findElement(By.className(val));
		Actions builder = new Actions(driver);
		builder.moveToElement(mouseover).build().perform();
		er.reportStep("The mouse over on :"+val+" is successfully performed", "Passed");
	}
	
	
	
}

