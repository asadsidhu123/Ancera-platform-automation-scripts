
package org.Tests;


import java.io.IOException;
import java.time.Duration;


import Config.Base_Test;
import Config.ReadPropertyFile;
import Misc.Constants;
import Misc.Functions;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import Misc.DateUtil;
import Pages.Side_Menu;
import Pages.Login_Page;
import org.aeonbits.owner.ConfigFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static Misc.ExtentVariables.*;
import static Misc.Functions.*;
import static Models.LoginModel.*;
import static Pages.Login_Page.user_name;


public class Login_Test extends Base_Test
{
    public static ReadPropertyFile config = ConfigFactory.create(ReadPropertyFile.class);
    @BeforeTest
public void extent() throws InterruptedException, IOException {
    spark = new ExtentSparkReporter("target/Reports/Login/Login_"+ DateUtil.date+".html");
    spark.config().setReportName("Login Test Report");
}
    @Test (priority = 1)
    public static void Login() throws InterruptedException, IOException {
        try {
            test = extent.createTest("AN-01: Verify that user is able to login with valid credentials");
            steps = test.createNode(Scenario.class, Steps);
            results = test.createNode(Scenario.class, Results);

            steps.createNode("1. Navigate to Login Screen");
            steps.createNode("2. Enter Valid User Name");
            steps.createNode("3. Enter Valid Password");
            steps.createNode("4. Click on Login Button.");
            SoftAssert softAssert = new SoftAssert();
            Login_Page lp = new Login_Page(driver);
            Side_Menu side_menu = new Side_Menu(driver);
            lp.set_username(Constants.login_email);
            lp.set_password(Constants.login_password);
            lp.click_login();
            waitUntilInvisible(side_menu.loader);
            waitUntilVisible(side_menu.profileSettingsBtn);
            Thread.sleep(5000);
            softAssert.assertAll();
            Assert.assertTrue(driver.findElement(side_menu.profileSettingsBtn).isDisplayed());
            test.pass("Login Test Verified Successfully");
            results.createNode("Login Test Verified Successfully");
            captureScreen();
            saveResult(ITestResult.SUCCESS, null);
        }catch(AssertionError er){
            test.fail("Login Test failed to verify");
            results.createNode("Login Test failed to verify");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("Login Test failed to verify");
            results.createNode("Login Test failed to verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }
    @Test(priority = 2)
        public void Logout () throws IOException {
        try {
            Functions functions = new Functions(driver);
            test = extent.createTest("AN-01: Verify that user is able to logout.");
            steps = test.createNode(Scenario.class, Steps);
            results = test.createNode(Scenario.class, Results);
            steps.createNode("1. Click on Logout Button");
            steps.createNode("2. Confirm Logout");
            steps.createNode("3. Verify that user gets logged out");
            Login_Page lp = new Login_Page(driver);
            Side_Menu side_menu = new Side_Menu(driver);
            Thread.sleep(Duration.ofSeconds(2));
            scrollUntilVisible(side_menu.logoutBtn);
            side_menu.click_logout();
            Thread.sleep(Duration.ofSeconds(2));
            side_menu.confirm_logout();
            waitUntilVisible(user_name);
            captureScreen();
            test.pass("User Successfully logged out");
            results.createNode("Logout Test Verified Successfully");
            saveResult(ITestResult.SUCCESS, null);
        }catch(AssertionError er){
            test.fail("User does not Logout");
            results.createNode("Logout Test case failed to verify");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("User does not Logout");
            results.createNode("Logout Test case failed to verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }
    @Test(priority = 3)
    public void Negative_Cases () throws InterruptedException, IOException {
        try {
            Login_Page lp = new Login_Page(driver);
            Side_Menu side_menu = new Side_Menu(driver);

            for (int i=0; i<lstlogin.size();i++){
                test = extent.createTest(lstlogin.get(i).testCaseName);
                Thread.sleep(Duration.ofSeconds(2));
//                steps = test.createNode(Scenario.class, Steps);
//                results = test.createNode(Scenario.class, Results);
                lp.set_username(lstlogin.get(i).uname);
                lp.set_password(lstlogin.get(i).pwd);
                lp.click_login();
                waitUntilVisible(lp.alertMessage);
                Assert.assertEquals(lp.get_alert_message(), "Sorry, we don't recognize these credentials");
                test.pass("User receives an alert message");
//                results.createNode("Forgot Password Test Verified Successfully");
                saveResult(ITestResult.SUCCESS, null);
            }

        }catch(AssertionError er){
            test.fail("User does not receive an alert message");
            results.createNode("Test case failed to verify");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("User does not receive an alert message");
            results.createNode("Test case failed to verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }
    @AfterTest
    public static void endreport() {
        extent.flush();
    }
}
