
package org.Tests;

import Config.Base_Test;
import Config.ReadPropertyFile;
import Misc.Constants;
import Misc.Functions;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import Misc.DateUtil;
import Pages.Forgot_Password_Page;
import Pages.Login_Page;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import static Misc.ExtentVariables.*;
import static Misc.Functions.*;
import static Models.ForgotPasswordModel.*;
import static Pages.Forgot_Password_Page.*;


public class Forgot_Password_Test extends Base_Test
{
    public static ReadPropertyFile config = ConfigFactory.create(ReadPropertyFile.class);
    @BeforeTest
public void extent() throws InterruptedException, IOException {
    spark = new ExtentSparkReporter("target/Reports/Forgot_Password/Forgot_Password_"+ DateUtil.date+".html");
    spark.config().setReportName("Forgot_Password Test Report");
}
    @Test (priority = 1)
    public void DeleteEmails () throws InterruptedException, IOException {
        try {
            test = extent.createTest("Delete All emails from gmail");
            steps = test.createNode(Scenario.class, Steps);
            results = test.createNode(Scenario.class, Results);
            Forgot_Password_Page fp = new Forgot_Password_Page(driver);
            ((JavascriptExecutor)driver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            fp.get_outlook_url();
            fp.set_outlook_email(Constants.fp_email);
            Thread.sleep(1500);
            driver.findElement(outlookEmail).sendKeys(Keys.ENTER);
            fp.set_outlook_password(Constants.fp_password);
            Thread.sleep(1500);
            driver.findElement(outlookPassword).sendKeys(Keys.ENTER);
            Thread.sleep(Duration.ofSeconds(2));
            fp.confirm_outlook_login();
            Thread.sleep(Duration.ofSeconds(2));
            driver.get("https://outlook.live.com/mail/0/");
            Thread.sleep(Duration.ofSeconds(15));
            fp.delete_all_emails();
//            Assert.assertEquals(dp.get_page_header(),"Hello Asad!");
            test.pass("All Emails Deleted Successfully");
            results.createNode("All Emails Deleted Successfully");
            captureScreen();
            driver.close();
            driver.switchTo().window(tabs.get(0));
            saveResult(ITestResult.SUCCESS, null);
        }catch(AssertionError er){
            test.fail("Emails failed to delete");
            results.createNode("Emails failed to delete");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("Emails failed to delete");
            results.createNode("Emails failed to delete");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }
    @Test(priority = 2)
        public void SendResetPasswordLink () throws IOException {
        Forgot_Password_Page fp = new Forgot_Password_Page(driver);
        Login_Page lp = new Login_Page(driver);
        lp.forgot_password_click();
        for (int i = 1; i < lstForgotPassword.size(); i++) {
            try {
                test = extent.createTest(lstForgotPassword.get(i).testCaseName);
                Thread.sleep(Duration.ofSeconds(2));
                fp.set_fp_email(lstForgotPassword.get(i).email);
                Thread.sleep(Duration.ofSeconds(2));
                fp.click_fp_submit();
                Thread.sleep(Duration.ofSeconds(5));
                switch (i) {
                    case 0:
                        Assert.assertEquals(driver.findElement(alertMessage).getText(), lstForgotPassword.get(i).alertMessage);
                        break;
                    case 1:
                        Assert.assertEquals(driver.findElement(mailSentMsg).getText(), lstForgotPassword.get(i).alertMessage);
                        break;
                    default:
                        // code block
                }
                Thread.sleep(Duration.ofSeconds(2));
                captureScreen();
                test.pass(lstForgotPassword.get(i).testCasePassMsg);
                saveResult(ITestResult.SUCCESS, null);
            } catch (AssertionError er) {
                test.fail(lstForgotPassword.get(i).testCaseFailMsg);
                saveResult(ITestResult.FAILURE, new Exception(er));
            } catch (Exception ex) {
                test.fail(lstForgotPassword.get(i).testCaseFailMsg);
                saveResult(ITestResult.FAILURE, ex);
            }
        }
    }
    @Test(priority = 3)
    public void VerifyEmailReceived () throws InterruptedException, IOException {
        Functions functions = new Functions(driver);
        try {
            test = extent.createTest("Verify That User Receives An Email with Reset Password Link.");
            steps = test.createNode(Scenario.class, Steps);
            steps.createNode("1. Login to the Email");
            steps.createNode("2. Verify that An Email is Received with the Reset Password Link");
            results = test.createNode(Scenario.class, Results);
            Forgot_Password_Page fp = new Forgot_Password_Page(driver);
            ((JavascriptExecutor)driver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            fp.get_outlook_url();
            Thread.sleep(1500);
            if(driver.findElements(outlookEmail).size() != 0){
                fp.set_outlook_email(config.fp_email());
                driver.findElement(outlookEmail).sendKeys(Keys.ENTER);
                Thread.sleep(1500);
                fp.set_outlook_password(config.fp_password());
                driver.findElement(outlookPassword).sendKeys(Keys.ENTER);
                Thread.sleep(1500);
                Thread.sleep(Duration.ofSeconds(2));
                fp.confirm_outlook_login();
            }
            driver.get("https://outlook.live.com/mail/0/");
            Thread.sleep(Duration.ofSeconds(15));
            fp.open_email_with_reset_password_link();
            Thread.sleep(Duration.ofSeconds(15));
            waitUntilVisible(resetPasswordLink);
            Assert.assertTrue(driver.findElement(resetPasswordLink).isDisplayed());

                test.pass("User Received an email with the Reset Password Link");
                results.createNode("Test Case Verified Successfully");
                saveResult(ITestResult.SUCCESS, null);
        }catch(AssertionError er){
            test.fail("User Did Not Receive the Email with Reset Password Link");
            results.createNode("Test Case Failed to Verify");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("User Did Not Receive the Email with Reset Password Link");
            results.createNode("Test Case Failed to Verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }
    @Test (priority = 4)
    public void ResetPassword () throws InterruptedException, IOException {
        try {
            Forgot_Password_Page fp = new Forgot_Password_Page(driver);
            test = extent.createTest("Verify that User is able to Reset Password Using Reset Password Link");
            results = test.createNode(Scenario.class, Results);
            steps = test.createNode(Scenario.class, Steps);
            steps.createNode("1. Login to the email.");
            steps.createNode("2. Open the Reset Password link from received email.");
            steps.createNode("3. Set the Password and Confirm it.");
            if(driver.findElements(resetPasswordLink).size() != 0){
                fp.open_reset_password_link();
                Thread.sleep(1500);
                ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
                driver.switchTo().window(tabs.get(2));
                fp.set_new_password(Constants.fp_password);
                fp.confirm_new_password(Constants.fp_password);
                driver.findElement(resetPasswordField).sendKeys(Keys.ENTER);
                Thread.sleep(1500);
            }
            Assert.assertTrue(driver.findElement(passwordChangedMsg).isDisplayed());
            test.pass("User is able to successfully reset the password.");
            results.createNode("Test Case Verified Successfully");
            saveResult(ITestResult.SUCCESS, null);
        }catch (AssertionError er){
            test.fail("User is not able to reset their password");
            results.createNode("Test Case Failed to Verify");
            saveResult(ITestResult.FAILURE, new Exception(er));
        }catch (Exception ex){
            test.fail("User is not able to reset their password");
            results.createNode("Test Case Failed to Verify");
            saveResult(ITestResult.FAILURE, ex);        }
    }
        @AfterTest
    public static void endreport() {
        extent.flush();
    }
}
