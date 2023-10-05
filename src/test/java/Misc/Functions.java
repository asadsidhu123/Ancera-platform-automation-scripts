package Misc;

import Config.Base_Test;
import org.bouncycastle.oer.its.etsi102941.basetypes.PublicKeys;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Functions extends Base_Test {
    public static WebDriver ldriver;


    public Functions(WebDriver rdriver)
    {
        ldriver=rdriver;
        PageFactory.initElements(rdriver, this);
    }
    public static void UrlNavigation(String URL, WebDriver driver){
        String currentUrl = driver.getCurrentUrl();

        // Check if the URL contains "login"
        if (currentUrl.contains("login")) {
            // If the URL contains "login", skip
            System.out.println("Already on login page. Skipping...");
        } else {
            // If the URL doesn't contain "login", navigate to the login page
            driver.get("https://example.com/login");
            System.out.println("Navigated to login page.");
        }
    }
    public static void waitUntilVisible(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public static void waitUntilInvisible(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(35));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    public static void scrollUntilVisible(By locator){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].scrollIntoView()", driver.findElement(locator));

    }
    public static void hover(WebElement locator, int x, int y) {
        Actions action = new Actions(driver);
        action.moveToElement(locator, x, y).perform();
    }
    public static void hoverByOffset(int x, int y) {
        Actions action = new Actions(driver);
        action.moveByOffset(x,y).perform();
    }
    }
