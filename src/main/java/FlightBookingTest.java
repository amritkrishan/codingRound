import com.sun.javafx.PlatformUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FlightBookingTest {

    WebDriver driver = new ChromeDriver();

    @BeforeClass
    public void setUp(){
        setDriverPath();
        driver.get("https://www.cleartrip.com/");
        waitFor(2000);
    }

    @Test
    public void testThatResultsAppearForAOneWayJourney() {
        driver.findElement(By.id("OneWay")).click();
        driver.findElement(By.id("FromTag")).clear();
        driver.findElement(By.id("FromTag")).sendKeys("Bangalore");
        //wait for the auto complete options to appear for the origin
        waitFor(2000);
        List<WebElement> originOptions = driver.findElement(By.id("ui-id-1")).findElements(By.tagName("li"));
        originOptions.get(0).click();
        //Changed identifier from toTag to ToTag
        driver.findElement(By.id("ToTag")).clear();
        driver.findElement(By.id("ToTag")).sendKeys("Delhi");
        //wait for the auto complete options to appear for the destination
        waitFor(2000);
        //select the first item from the destination auto complete list
        List<WebElement> destinationOptions = driver.findElement(By.id("ui-id-2")).findElements(By.tagName("li"));
        destinationOptions.get(0).click();
        //Fetching current date in order to use it as Flight search date (Can be parameterized as well)
        Date date = new Date();
        String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        String currentDate=modifiedDate.substring(modifiedDate.lastIndexOf('-')+1);
        //First clicking on date picker icon
        driver.findElement(By.xpath("//a/i[@class='icon ir datePicker']")).click();
        //Then selecting current date
        driver.findElement(By.xpath("//a[@class='ui-state-default ui-state-highlight ui-state-active ' and text()='"+currentDate+"']")).click();
        //all fields filled in. Now click on search
        driver.findElement(By.id("SearchBtn")).click();
        waitFor(5000);
        //verify that result appears for the provided journey search
        Assert.assertTrue(isElementPresent(By.className("searchSummary")));
    }


    private void waitFor(int durationInMilliSeconds) {
        try {
            Thread.sleep(durationInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void setDriverPath() {
        if (PlatformUtil.isMac()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
        }
        if (PlatformUtil.isWindows()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        }
        if (PlatformUtil.isLinux()) {
            System.setProperty("webdriver.chrome.driver", "chromedriver_linux");
        }
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
