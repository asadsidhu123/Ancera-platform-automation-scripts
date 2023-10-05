package org.Tests;

import Config.Base_Test;
import Config.ReadPropertyFile;
import Misc.Constants;
import Misc.DBConfig;
import Misc.DateUtil;
import Misc.Functions;
import Pages.Side_Menu ;
import Pages.Login_Page;
import Pages.Profile_Page;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.aeonbits.owner.ConfigFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.sql.ResultSet;
import java.time.Duration;

import static Config.Base_Test.saveResult;
import static Misc.ExtentVariables.*;
import static Misc.ExtentVariables.extent;
import static Misc.Functions.scrollUntilVisible;
import static Misc.Functions.waitUntilVisible;
import static Models.EditProfileModel.lsteditprofile;
import static Models.LoginModel.*;
import static Pages.Login_Page.user_name;

public class Profile_Test extends Base_Test {

    public static ReadPropertyFile config = ConfigFactory.create(ReadPropertyFile.class);
    @BeforeTest
    public void extent() throws InterruptedException, IOException {
        spark = new ExtentSparkReporter("target/Reports/Profile/Profile_"+ DateUtil.date+".html");
        spark.config().setReportName("Profile Test Report");
    }
    @BeforeClass
    public void Login() throws InterruptedException, IOException {
        Login_Test.Login();
    }
    @Test(priority = 1)
    public void EditProfile () throws InterruptedException, IOException {
        try {
            Side_Menu side_menu = new Side_Menu (driver);
            Profile_Page pp = new Profile_Page(driver);
            Login_Page lp = new Login_Page(driver);
            Thread.sleep(15000);
            side_menu.click_profile_settings();
            Thread.sleep(Duration.ofSeconds(2));
            for (int i=0; i<lsteditprofile.size();i++) {
                test = extent.createTest(lsteditprofile.get(i).testCaseName);
                steps = test.createNode(Scenario.class, Steps);
                results = test.createNode(Scenario.class, Results);
                pp.set_user_first_name(lsteditprofile.get(i).ufname);
                pp.set_user_last_name(lsteditprofile.get(i).ulname);
                pp.set_country_name(lsteditprofile.get(i).ucountry);
                pp.click_save_settings();
                waitUntilVisible(pp.notification);
                Assert.assertEquals(pp.get_notification_text(),"Profile updated successfully");
                Thread.sleep(Duration.ofSeconds(10));
//                ResultSet resultSet = DBConfig.DBConfigure();
//                while(resultSet.next()) {
//
//                    String userFirstName = resultSet.getString("userFirstName");
//                    String userLastName = resultSet.getString("userLastName");
//
//                    System.out.println(userFirstName);
//                    System.out.println(userLastName);
//                }
                steps.createNode("1. Navigate to Profile Screen");
                steps.createNode("2. Enter all Valid info");
                steps.createNode("3. Click on Save Settings Button.");

                test.pass("Edit Profile Test Verified Successfully");
                results.createNode("Edit Profile Test Verified Successfully");
                captureScreen();
                saveResult(ITestResult.SUCCESS, null);
            }
        }catch(AssertionError er){
            test.fail("Edit Profile Test failed to verify");
            captureScreen();
            results.createNode("Edit Profile Test failed to verify");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("Edit Profile Test failed to verify");
            results.createNode("Edit Profile Test failed to verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }

    @AfterTest
    public static void endreport() {
        extent.flush();
    }
}
