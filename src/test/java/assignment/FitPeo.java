package assignment;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import io.github.bonigarcia.wdm.WebDriverManager;

public class FitPeo {
	@Test
	public void fitPeo_Test() throws Throwable {
		
		WebDriverManager.chromedriver().setup();
		
		//Launching the browser
		WebDriver driver=new ChromeDriver();
		//maximizing the window
		driver.manage().window().maximize();
		
		//1.Accessing FitPeo Homepage
		driver.get("https://www.fitpeo.com/");
		
		//Providing implicit wait
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		Thread.sleep(5000);
		
		//2.Navigating to the Revenue Calculator Page
		driver.findElement(By.xpath("//div[text()='Revenue Calculator']")).click();
		Thread.sleep(5000);
		
		//3.Scrolling down to the Slider Section
		WebElement sliderSection = driver.findElement(By.xpath("//h4[text()='Medicare Eligible Patients']"));
		JavascriptExecutor js=(JavascriptExecutor)driver; //downcasting the driver variable to use JavascriptExecutor Interface properties
		
		js.executeScript("arguments[0].scrollIntoView();", sliderSection);
		Thread.sleep(5000);
		
		//4.Adjusting the slider to 820
		WebElement slider=driver.findElement(By.xpath("//span[@class='MuiSlider-thumb MuiSlider-thumbSizeMedium MuiSlider-thumbColorPrimary MuiSlider-thumb MuiSlider-thumbSizeMedium MuiSlider-thumbColorPrimary css-1sfugkh']"));
        Actions action=new Actions(driver);
        
        //The X ad Y coordinates were fetched by Page Ruler extension in Chrome.
        //The default x coordinate(x=813) at value=200 and the x coordinate (x=906) at value=820 were recorded using Page ruler extension. Then the earlier x coordinate was subtracted from the latter one (906-813=93).
        //The same value is used for xOffset.
       
        action.dragAndDropBy(slider,93,0).build().perform();
        
        Thread.sleep(5000);
		
		//5.Updating the TextField
		WebElement textField = driver.findElement(By.xpath("//input[@class='MuiInputBase-input MuiOutlinedInput-input MuiInputBase-inputSizeSmall css-1o6z5ng']"));
	    textField.click();
	    Thread.sleep(3000);
		String value = textField.getAttribute("value");
		if(value!=null)
		{
		      int valueLen = value.length();
		      for(int i=0;i<valueLen;i++)
		      {
		    	  textField.sendKeys(Keys.BACK_SPACE);
		      }
		}
		String textValue="560";
		textField.sendKeys(textValue);
		Thread.sleep(5000);
		
		//6.Validate the slider value
		WebElement sliderPosition=driver.findElement(By.xpath("//span[@class='MuiSlider-thumb MuiSlider-thumbSizeMedium MuiSlider-thumbColorPrimary MuiSlider-thumb MuiSlider-thumbSizeMedium MuiSlider-thumbColorPrimary css-1sfugkh']/input"));
		String sliderValue = sliderPosition.getAttribute("value");
		
		//Using TestNg class SoftAssert for validation
		SoftAssert sa=new SoftAssert();
		sa.assertEquals(textValue, sliderValue);
		Thread.sleep(1000);
		
		//7.Selecting CPT codes
		 WebElement CPT_99091=driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 inter css-1s3unkt']"));
		 js.executeScript("arguments[0].scrollIntoView();",CPT_99091 );
		 Thread.sleep(3000);
		 driver.findElement(By.xpath("//p[text()='CPT-99091']/../descendant::input")).click();
		 Thread.sleep(3000);
		 driver.findElement(By.xpath("//p[text()='CPT-99453']/../descendant::input")).click();
		 Thread.sleep(3000);
		 driver.findElement(By.xpath("//p[text()='CPT-99454']/../descendant::input")).click();
		 Thread.sleep(3000);
		 driver.findElement(By.xpath("//p[text()='CPT-99474']/../descendant::input")).click();
		 Thread.sleep(5000);
		 
		 //8.Validating total Recurring Reimbursement
		 //So According to the 8th and 9th Test case and the image provided, the number of Total Individual Patient/Month is 820.
		 //So going according to the test case, we will first update the number of Medicare Eligible Patients to 820
		
		 String value2 = textField.getAttribute("value");
			if(value2!=null)
			{
			      int valueLen = value.length();
			      for(int i=0;i<valueLen;i++)
			      {
			    	  textField.sendKeys(Keys.BACK_SPACE);
			      }
			}
			textField.sendKeys("820");
			Thread.sleep(3000);
			
			//Scrolling down so that the header gets visible
			js.executeScript("arguments[0].scrollIntoView();",CPT_99091);
			
			 String actualTotalRecurringReimbursement=driver.findElement(By.xpath("(//p[@class='MuiTypography-root MuiTypography-body1 inter css-1bl0tdj'])[4]")).getText();
			 String expectedTotalRecurringReimbursement="$110700";
			 
			 sa.assertEquals(expectedTotalRecurringReimbursement,actualTotalRecurringReimbursement);
			 
	         Thread.sleep(5000);
	         driver.close();
		 
		 
		
		 sa.assertAll();
		//updated code
		
	}

}
