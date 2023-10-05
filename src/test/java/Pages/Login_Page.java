
package Pages;

import Config.ReadPropertyFile;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Login_Page {

    WebDriver driver;
    ReadPropertyFile config = ConfigFactory.create(ReadPropertyFile.class);

    public Login_Page(WebDriver rdriver)
    {
        driver=rdriver;
        PageFactory.initElements(rdriver, this);
    }

    public static By user_name = By.name("email");
    public static By password = By.name("password");
    public static By loginBtn = By.xpath("//button[text()='Sign In']");
    public static By alertMessage = By.xpath("//*[@id=\"notification\"]/span");
    public static By forgotPassword = By.xpath("//*[text()='Forgot password?']");

    public static By pageTitle = By.name("frmLogin");





    public void set_username (String uname){
        driver.findElement(user_name).clear();
        driver.findElement(user_name).sendKeys(uname);
    }
    public void set_password (String pwd){
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pwd);
    }
    public void click_login (){
        driver.findElement(loginBtn).click();
    }
    public void forgot_password_click (){
        driver.findElement(forgotPassword).click();
    }
    public String get_alert_message (){
       return driver.findElement(alertMessage).getText();
    }

}









