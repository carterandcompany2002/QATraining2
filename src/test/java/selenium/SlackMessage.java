package selenium;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class SlackMessage {


    private WebDriver driver;


    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
 /*      WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("dom.webnotifications.enabled", false);  // Disable desktop notifications

        driver = new FirefoxDriver(options);*/
    }

    @Test
    public void loginToSlack() {

        driver.get("https://slack.com/signin#/signin");


        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(org.openqa.selenium.NoSuchElementException.class);

        wait.until(ExpectedConditions.titleContains("Login | Slack"));

        String title = driver.getTitle();
        String Expected = "Login | Slack";
        Assert.assertEquals(title,Expected);


        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("/html/body/div[1]/div[1]/div[2]/div[4]/span"),"sign in manually instead."));

        driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[4]/span/a")).click();
        driver.findElement(By.xpath("//*[@id=\"domain\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"domain\"]")).sendKeys("creativecodingcollect");
        driver.findElement(By.xpath("/html/body/main/div/div/div/div[1]/div[2]/form/button")).click();
        driver.findElement(By.xpath("//*[@id=\"page_contents\"]/div/div/div[2]/div[3]/div[4]/span/a")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        driver.findElement(By.xpath("//*[@id=\"email\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("kcarter10@gmail.com ");
        driver.findElement(By.xpath("//*[@id=\"password\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("Chicago1");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath("//*[@id=\"signin_btn\"]")).click();

        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("/html/body/div[1]/div/div/div[2]/p/a")).click();


        driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[2]/div[2]/div[1]/div[2]/div/div[1]/div/div/div[1]/div/div/div[13]/div")).click();
        //Click on the user you want to send a message
        //driver.findElement(By.xpath("//*[@id=\"Pdms\"]")).click();
        driver.findElement(By.cssSelector("#D05MYLH0Z3M")).click();
        //D05N16WNC5S D05NBCKHM6D id="D05MYLH0Z3M"
      /*  WebElement toBox=driver.findElement(By.xpath("//*[@id=\"recent_page__select\"]"));
        toBox.click();
        toBox.sendKeys("@HARSH");
        toBox.sendKeys(Keys.ENTER); */

        WebElement element = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[5]/div[2]/div/div[4]/div/div[2]/div/div/div/div[2]/div/div/div/div[2]/div/div[1]"));

        element.click();
        element.sendKeys("Hi Harsh this is an automated test message");
        element.sendKeys(Keys.ENTER);
    }
/*
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }*/
}


