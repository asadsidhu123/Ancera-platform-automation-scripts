
package Pages;

import Misc.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

import static Misc.Functions.waitUntilVisible;

public class Forgot_Password_Page {

    WebDriver ldriver;

    public Forgot_Password_Page(WebDriver rdriver)
    {
        ldriver=rdriver;
        PageFactory.initElements(rdriver, this);
    }

    public static By fpEmail = By.name("email");
    public static By fpSubmitBtn = By.id("btn-reset");
    public static By alertMessage = By.xpath("//*[@id=\"notification\"]/span");
    public static By mailSentMsg = By.xpath("//*[@id=\"mail-sent\"]/h1");
    public static By outlookEmail = By.name("loginfmt");
    public static By outlookPassword = By.name("passwd");
    public static By outlookconfirmLogin = By.id("idSIButton9");
    public static By outlooknumberOfEmails = By.xpath("//*[name()='div' and contains(@class, 'zKDWD YbB6r IKvQi IjQyD EhiOs JCRRb')]");

    public static By outlookSelectAllBtn = By.xpath("//*[@id=\"MainModule\"]/div/div/div[3]/div/div/div[1]/div[1]/div[1]/div/div[1]/div/div/i[2]");
    public static By outlookDeleteBtn = By.xpath("//*[@id=\"innerRibbonContainer\"]/div[1]/div/div/div/div[2]/div/div/span/button[1]");
    public static By outlookconfirmDelete = By.id("ok-1");
    public static By emailWithResetPasswordLink = By.xpath("//*[text() = 'ancera.org.dev@gmail.com']");
    public static By resetPasswordLink = By.xpath("//*[text() = 'Reset Password']");
    public static By resetPasswordField = By.id("password");
    public static By resetPasswordConfirmField = By.id("confirmpassword");
    public static By passwordChangedMsg = By.xpath("//*[text() = 'Password changed']");




    public void get_outlook_url (){
        ldriver.get(Constants.OUTLOOK_LOGIN_URL);
    }

    public void set_outlook_email (String email){
        ldriver.findElement(outlookEmail).clear();
        ldriver.findElement(outlookEmail).sendKeys(email);
    }
    public void set_outlook_password (String password){
        ldriver.findElement(outlookPassword).clear();
        ldriver.findElement(outlookPassword).sendKeys(password);
    }
    public void confirm_outlook_login (){
        ldriver.findElement(outlookconfirmLogin).click();
    }
    public void delete_all_emails () throws InterruptedException {
        if (ldriver.findElements(outlooknumberOfEmails).size()!=0) {
        waitUntilVisible(outlookSelectAllBtn);
//        if(ldriver.findElements(outlookSelectAllBtn).size()!= 0){
            ldriver.findElement(outlookSelectAllBtn).click();
//            Thread.sleep(1500);
            waitUntilVisible(outlookDeleteBtn);
//            if (ldriver.findElements(outlookDeleteBtn).size() != 0) {
                ldriver.findElement(outlookDeleteBtn).click();
//                Thread.sleep(1500);
                waitUntilVisible(outlookconfirmDelete);
//                if (ldriver.findElements(outlookconfirmDelete).size() != 0) {
                    ldriver.findElement(outlookconfirmDelete).click();
                    Thread.sleep(Duration.ofSeconds(2));
//                }
//            }
//        }
        }
        else {
            System.out.println("N0 EmaiIs");
        }
    }
    public void set_fp_email (String email){
        ldriver.findElement(fpEmail).clear();
        ldriver.findElement(fpEmail).sendKeys(email);
    }
    public void click_fp_submit (){
        ldriver.findElement(fpSubmitBtn).click();
    }
    public void open_email_with_reset_password_link (){
        ldriver.findElement(emailWithResetPasswordLink).click();
    }
    public void open_reset_password_link (){
        waitUntilVisible(resetPasswordLink);
        ldriver.findElement(resetPasswordLink).click();
    }
    public void set_new_password (String pwd){
        ldriver.findElement(resetPasswordField).clear();
        ldriver.findElement(resetPasswordField).sendKeys(pwd);
    }
    public void confirm_new_password (String pwd){
        ldriver.findElement(resetPasswordConfirmField).clear();
        ldriver.findElement(resetPasswordConfirmField).sendKeys(pwd);
    }


}









