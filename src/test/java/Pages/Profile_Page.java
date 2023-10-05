package Pages;


import Misc.Functions;
import Misc.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Profile_Page

{
    WebDriver ldriver;

    public Profile_Page(WebDriver rdriver)
    {
        ldriver=rdriver;
        PageFactory.initElements(rdriver, this);
    }

    public static By userFirstName = By.id("userFirstName");
    public static By userLastName = By.id("userLastName");
    public static By userEmail = By.id("userEmail");
    public static By userCountry = By.name("region");
    public static By saveSettingsBtn = By.xpath("//*[text()='Save Settings']");
    public static By notification = By.id("notification");



    public void set_user_first_name (String fname){
        ldriver.findElement(userFirstName).clear();
        ldriver.findElement(userFirstName).sendKeys(fname);
    }
    public void set_user_last_name (String lname){
        ldriver.findElement(userLastName).clear();
        ldriver.findElement(userLastName).sendKeys(lname);
    }
    public void set_country_name (String country){
        Select drpCountry = new Select(ldriver.findElement(userCountry));
        drpCountry.selectByVisibleText(country);
    }
    public void click_save_settings (){
        ldriver.findElement(saveSettingsBtn).click();
    }
    public String get_notification_text (){
       return ldriver.findElement(notification).getText();
    }



}
