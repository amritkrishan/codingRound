import com.sun.javafx.PlatformUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SignInTest {

    WebDriver driver = new ChromeDriver();

    @BeforeClass
    public void setUp(){
        setDriverPath();
        driver.get("https://www.cleartrip.com/");
        waitFor(2000);
    }

    @Test
    public void shouldThrowAnErrorIfSignInDetailsAreMissing() {
        driver.findElement(By.linkText(SignIn.YOUR_TRIPS)).click();
        driver.findElement(By.xpath(SignIn.SIGN_IN_BTN)).click();
        //Switching to iframe that has Login pop up
        driver.switchTo().frame(SignIn.IFRAME_NAME);
        waitFor(2000);
        driver.findElement(By.xpath(SignIn.SIGN_IN_BTN_IFRAME)).click();
        //Changed xpath as the text bis tied up to span
        String errors1 = driver.findElement(By.xpath(SignIn.ERROR_TEXT)).getText();
        Assert.assertTrue(errors1.contains(AssertionMessages.error1));
        driver.switchTo().parentFrame();
    }

    private void waitFor(int durationInMilliSeconds) {
        try {
            Thread.sleep(durationInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
