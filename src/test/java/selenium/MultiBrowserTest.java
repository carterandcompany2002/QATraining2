package selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class MultiBrowserTest {

    private WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Invalid Browser Name: " + browser);
        }
    }
    @Test
    public  void  testExample(){
        driver.get("https://www.yahoo.com");
        String Expected = "Yahoo | Mail, Weather, Search, Politics, News, Finance, Sports & Videos";
        String Actual = driver.getTitle();

        Assert.assertEquals(Expected, Actual);
        driver.manage().window().minimize();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500);");

        long loadline = (Long) js.executeScript("return window.performance.timing.loadEventEnd - "
                + "window.performance.timing.navigationStart;");

        System.out.println("Website Load Time: " +loadline);
    }

    @AfterMethod
    public void tearDown() {
        if(driver != null) {
            driver.quit();
        }
    }
}
