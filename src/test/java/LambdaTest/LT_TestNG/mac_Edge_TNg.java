package LambdaTest.LT_TestNG;

import java.net.URL;
import java.util.HashMap;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.lambdatest.tunnel.Tunnel;

public class mac_Edge_TNg{
    WebDriver driver;
    Tunnel t;
    String URL = "https://www.lambdatest.com/selenium-playground/";
    
    @Parameters({"browserVersion", "platform","username","access_key"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(String browserVersion,String platform,String username,String access_key ) throws Exception { 
    	
    	EdgeOptions browserOptions = new EdgeOptions();
    	browserOptions.addArguments("--ignore-certificate-errors");
		browserOptions.setPlatformName(platform);
		browserOptions.setBrowserVersion(browserVersion);
		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
		ltOptions.put("username",username);
		ltOptions.put("accessKey", access_key);
		ltOptions.put("build", "Build TestNG Edge - "+platform);
		ltOptions.put("name", "TC Run TestNG -" + platform);
		ltOptions.put("network",true);
		ltOptions.put("console",true);
		ltOptions.put("visual", true);
		ltOptions.put("w3c", true);
		browserOptions.setCapability("LT:Options", ltOptions);


		//create tunnel instance
		t = new Tunnel();
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("user", username);
		options.put("key", access_key);

		//start tunnel
		t.start(options);
	
		driver = new RemoteWebDriver(new URL("https://" + username + ":" + access_key + "@hub.lambdatest.com/wd/hub"), browserOptions);

		System.out.println("Started session for Edge");
		
    }

    @Test( timeOut = 20000)
    public void Scenario1() throws InterruptedException {
		System.out.println("Started senario 1");		
		driver.get(URL);
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 20000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Simple Form Demo")));

        
		SoftAssert softAssert = new SoftAssert();
		String title = driver.getTitle();
		softAssert.assertEquals(title, "LambdaTest", "Page Title is not as expected!");
		softAssert.assertAll();

    }

    @Test(timeOut = 20000)
    public void Scenario2() throws InterruptedException {  
    	System.out.println("Started senario 2");
		driver.get(URL);
		Thread.sleep(3000);
    	driver.findElement(By.linkText("Checkbox Demo")).click();
    	WebElement checkbox = driver.findElement(By.id("isAgeSelected"));
    	checkbox.click();
    	Assert.assertTrue(checkbox.isSelected(), "Checkbox is not selected!");
    	checkbox.click();
    	Assert.assertFalse(checkbox.isSelected(), "Checkbox is still selected!");

    }

    @Test(timeOut = 20000)
    public void Scenario3() throws InterruptedException{
		System.out.println("Started senario 3");
		driver.get(URL);
		Thread.sleep(3000);
        driver.findElement(By.linkText("Javascript Alerts")).click();
        driver.findElement(By.xpath("//button[text()='Click Me']")).click();
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals(alert.getText(), "I am an alert box!","Alert message not as expected!");
        alert.accept();

       
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
          }
    }
}
