import com.sun.javafx.PlatformUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HotelBookingTest {

    WebDriver driver = new ChromeDriver();
    //Changed access modifier from private to public
    @FindBy(how=How.LINK_TEXT, using="Hotels")
    public WebElement hotelLink;

    @FindBy(id = "Tags")
    public WebElement localityTextBox;

    @FindBy(id = "SearchHotelsButton")
    public WebElement searchButton;

    @FindBy(id = "travellersOnhome")
    public WebElement travellerSelection;

    @BeforeClass
    public void setUp(){
        setDriverPath();
        //Initializing Page Factory Elements so that it can be used within class
        PageFactory.initElements(driver, this);
        driver.get("https://www.cleartrip.com/");
        waitFor(2000);
    }

    @Test
    public void shouldBeAbleToSearchForHotels() {
        hotelLink.click();
        localityTextBox.sendKeys("Indiranagar, Bangalore");
        new Select(travellerSelection).selectByVisibleText("1 room, 2 adults");
        searchButton.click();
        driver.quit();
    }

    //Added wait function
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
