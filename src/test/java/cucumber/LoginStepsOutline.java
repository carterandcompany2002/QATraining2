package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;


import java.time.Duration;

public class LoginStepsOutline {
        private WebDriver driver;

        @Given("The user is on the OrangeHRM login page")
        public void theUserIsOnTheOrangeHRMLoginPage() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        }

        @When("the user enters {string} and {string}")
        public void theUserEntersAnd(String arg0, String arg1) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            WebElement usernameField = driver.findElement(By.name("username"));
            WebElement passwordField = driver.findElement(By.name("password"));

            usernameField.sendKeys("admin");
            passwordField.sendKeys("admin123");

        }

        @And("clicks the login button")
        public void clicksTheLoginButton() {
            WebElement loginButton = driver.findElement(By.xpath("/html/body/div/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button"));
            loginButton.click();
        }

        @Then("the user should be logged in successfully")
        public void theUserShouldBeLoggedInSuccessfully() {
            Wait<WebDriver> wait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(10))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(org.openqa.selenium.NoSuchElementException.class);

            WebElement dashboardTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[1]/span/h6")));

            Assert.assertTrue(dashboardTitle.isDisplayed(), "Login was not successful.");

        }
        @After
        public void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }
}
