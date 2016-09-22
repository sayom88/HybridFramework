package config;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;

import static executionEngine.DriverScript.OR;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import executionEngine.DriverScript;
import utility.Log;

public class ActionKeywords {
	
		public static WebDriver driver;
			

	public static void openBrowser(String object,String data){	 	
		Log.info("Opening Browser");
		try{				
			if(data.equals("Firefox")){
				System.setProperty("webdriver.gecko.driver", "C:\\ECLIPSE_WORKSPACE\\geckodriver-v0.10.0-win64\\geckodriver.exe");
				driver=new FirefoxDriver();
				   //Maximize the browser
				  driver.manage().window().maximize();
				  Thread.sleep(1000);
				  captureScreenShot(driver);
				Log.info("Mozilla browser started");				
				}
			else if(data.equals("IE")){
				//Dummy Code, Implement you own code
				System.setProperty("webdriver.ie.driver","C://ECLIPSE_WORKSPACE//IEDriverServer.exe");
				driver=new InternetExplorerDriver();
				 Thread.sleep(1000);
				captureScreenShot(driver);
				Log.info("IE browser started");
				}
			else if(data.equals("Chrome")){
				//Dummy Code, Implement you own code
				System.setProperty("webdriver.chrome.driver", "C:/ECLIPSE_WORKSPACE/chromedriver.exe");
				//driver = new ChromeDriver();
				driver=new ChromeDriver();
				 //Maximize the browser
				driver.manage().window().maximize();
				 Thread.sleep(1000);
				captureScreenShot(driver);
				Log.info("Chrome browser started");
				}
			
			   System.out.println("WebDriver is:"+driver);
			  //  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			int implicitWaitTime=(80);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		}catch (Exception e){
			Log.info("Not able to open the Browser --- " + e.getMessage());
			DriverScript.bResult = false;
		}
	}
	
	public static void navigate(String object, String data){
		try{
			Log.info("Navigating to URL");
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			driver.get(data);
			//driver.get(Constants.URL);
			Thread.sleep(1000);
			captureScreenShot(driver);
			// driver.navigate().to(Constants.URL);
		}catch(Exception e){
			Log.info("Not able to navigate --- " + e.getMessage());
			DriverScript.bResult = false;
			}
		}
	
	public static void click(String object1, String data){
		try{
			
			Thread.sleep(2000);
			
			String test=object1.toString();
			
			Log.info("Clicking on Webelement "+ test);
			
			System.out.println("Clicking on Webelement:"+ test);
			
			System.out.println("...................Click Event Started........");
			
			String value="";
			Enumeration e = OR.keys();

			System.out.println("Enumeration:"+e);
			
			while(e.hasMoreElements())
			{
				//System.out.println(e.nextElement());
			String param = (String) e.nextElement();
			
			System.out.println(param);
			
			if (param.equals(test.toString()))
			{
				System.out.println("Into If");
				value=(String) OR.get(param.toString());
				//System.out.println(value);
				break;
		
			}
			//break;
			}
			System.out.println(value);
			
	
			
			System.out.println("...............Click Event Completed........");
			
			driver.findElement(By.xpath(value)).click();
			
			captureScreenShot(driver);
			
			Thread.sleep(2000);
			
			
			
		 }catch(Exception e){
 			Log.error("Not able to click --- " + e.getMessage());
 			DriverScript.bResult = false;
         	}
		}
	
	public static void input(String object, String data){ 
		try{
			
			String test=object;
			
			Log.info("Entering the text in " + object);
			
	System.out.println("...................Input Event Started........");
			
	
	String value="";
	Enumeration e = OR.keys();

	System.out.println("Enumeration:"+e);
	
	while(e.hasMoreElements())
	{
		//System.out.println(e.nextElement());
	String param = (String) e.nextElement();
	
	System.out.println(param);
	
	if (param.equals(test.toString()))
	{
		System.out.println("Into If");
		value=(String) OR.get(param.toString());
		//System.out.println(value);
		break;

	}
	//break;
	}
	System.out.println(value);
			//System.out.println(OR.getProperty(object));
			
			System.out.println("...............Input Event Completed........");
			
			//driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			driver.findElement(By.xpath(value)).sendKeys(data);
			Thread.sleep(2000);
			
			captureScreenShot(driver);
			
		 }catch(Exception e){
			 Log.error("Not able to Enter UserName --- " + e.getMessage());
			 DriverScript.bResult = false;
		 	}
		}
	

	public static void waitFor(String object, String data) throws Exception{
		try{
			Log.info("Wait for 5 seconds");
			Thread.sleep(5000);
		 }catch(Exception e){
			 Log.error("Not able to Wait --- " + e.getMessage());
			 DriverScript.bResult = false;
         	}
		}

	public static void closeBrowser(String object, String data){
		try{
			
			Thread.sleep(5000);
			
			Log.info("Closing the browser");
			//driver.close();
		   driver.quit();
		   
		   Thread.sleep(8000);
		   
		 }catch(Exception e){
			 Log.error("Not able to Close the Browser --- " + e.getMessage());
			 DriverScript.bResult = false;
         	}
		}

	public static void captureScreenShot(WebDriver driver){
		// Take screenshot and store as a file format             
		 File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);   
		 
			System.out.println("src::"+src);
			
		try {
		// now copy the  screenshot to desired location using copyFile method
			
			String destfileloc=DriverScript.fileLocdir+"\\";
			
			System.out.println("destfileloc::"+destfileloc);
			
		FileUtils.copyFile(src, new File(destfileloc+DriverScript.sTestStepID+".png"));
		} catch (IOException e)
		 
		{
		  System.out.println(e.getMessage());
		 }
		  }
	
	}