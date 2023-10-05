package org.Tests;

import Config.Base_Test;
import Misc.DateUtil;
import Pages.Data_Quality_Dashboard_Page;
import Pages.Side_Menu;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//import io.opentelemetry.sdk.metrics.data.Data;
//import org.checkerframework.checker.units.qual.K;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static Misc.ExtentVariables.*;
import static Misc.ExtentVariables.steps;

public class Data_Quality_Dashboard_Test extends Base_Test {
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
    public void VerifyDates () throws InterruptedException, IOException {
        try {
            test = extent.createTest("AN-01: Verify that correct selected dates are shown with the data.");
            steps = test.createNode(Scenario.class, Steps);
            results = test.createNode(Scenario.class, Results);

            steps.createNode("1. Navigate to Dashboard Screen.");
            steps.createNode("2. Select Custom Dates from date picker.");
            steps.createNode("3. Verify that correct selected dates are shiown with the data.");
            Thread.sleep(Duration.ofSeconds(5));
            Side_Menu side_menu = new Side_Menu(driver);
            side_menu.click_data_quality_dashboard();
            Thread.sleep(Duration.ofSeconds(15));
            Data_Quality_Dashboard_Page dq = new Data_Quality_Dashboard_Page(driver);
            side_menu.select_date_range_option("Custom");
            side_menu.click_date_field();
            String expectedDate = side_menu.get_expected_selected_date();
            side_menu.select_start_date();
            side_menu.select_end_date();
            String actualDate = dq.get_actual_selected_date();
            Assert.assertEquals(actualDate, expectedDate);
            test.pass("Verify Dates Test Verified Successfully");
            results.createNode("Verify Dates Test Verified Successfully");
            captureScreen();
            saveResult(ITestResult.SUCCESS, null);
        }catch (AssertionError er){
            test.fail("Verify Dates Test failed to verify");
            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("Verify Dates Test failed to verify");
            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }
    @Test(priority = 2)
    public void VerifyDataQualityTabData () throws InterruptedException, IOException {
        try {
            test = extent.createTest("AN-01: Verify that correct data is displayed in the Data Quality Tab.");
            steps = test.createNode(Scenario.class, Steps);
            results = test.createNode(Scenario.class, Results);

            steps.createNode("1. Navigate to Dashboard Screen.");
            steps.createNode("2. Select Data Quality Tab.");
            steps.createNode("3. Verify that correct data is displayed in the Data Quality Tab.");
            Thread.sleep(Duration.ofSeconds(5));
            Data_Quality_Dashboard_Page dq = new Data_Quality_Dashboard_Page(driver);
            Map<String, String> elements2 = new HashMap<String, String>() {{
                put("nullCollectionDate", "36");
                put("collectionsWithNo12Samples", "0");
                put("noActiceFlocks", "24");
                put("MissingCyclingConfiguration", "0");
                put("CollectionDateOutOfRange", "0");
                put("samplesNoAssociatedPrograms", "0");
                put("flocksNoAssociatedPrograms", "17");
                put("moreThan65RockLength", "2");
                put("cartridgeWithNo12Lanes", "5");
                put("cartridgeWithLanesPending", "41");
                put("piperWithE03Errors", "0");
                put("numOfE06Errors", "0");
            }};
            for (String key : dq.get_data_quality_tab_actual_values().keySet()) {
                if (!elements2.containsKey(key) || !dq.get_data_quality_tab_actual_values().get(key).equals(elements2.get(key))) {
                    Assert.assertEquals(dq.get_data_quality_tab_actual_values().get(key),elements2.get(key));
                    System.out.println(elements2.get(key)+"-----"+dq.get_data_quality_tab_actual_values().get(key));
                }
                System.out.println(elements2.get(key)+"-----"+dq.get_data_quality_tab_actual_values().get(key));
                System.out.println("True");
            }
            test.pass("Verify Dates Test Verified Successfully");
            results.createNode("Verify Dates Test Verified Successfully");
            captureScreen();
            saveResult(ITestResult.SUCCESS, null);
        }catch (AssertionError er){
            test.fail("Verify Dates Test failed to verify");
            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("Verify Dates Test failed to verify");
            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }
    @Test(priority = 3)
    public void VerifySupportMonitoringTabData () throws InterruptedException, IOException {
        try {
            test = extent.createTest("AN-01: Verify that correct data is displayed in the Support Monitoring Tab.");
            steps = test.createNode(Scenario.class, Steps);
            results = test.createNode(Scenario.class, Results);

            steps.createNode("1. Navigate to Dashboard Screen.");
            steps.createNode("2. Select Data Quality Tab.");
            steps.createNode("3. Verify that correct data is displayed in the Support Monitoring Tab.");
            Data_Quality_Dashboard_Page dq = new Data_Quality_Dashboard_Page(driver);
            dq.click_support_monitoring_tab();
            Thread.sleep(Duration.ofSeconds(5));
            Map<String, String> elements2 = new HashMap<String, String>() {{
                put("cartridgeRunsOverErrors", "12/1");
                put("sampleRunsOverErrors", "51/1");
                put("samplesCollected", "51");
                put("activeFlocks", "53");
                put("collectionSites", "3226");
            }};
            for (String key : dq.get_support_monitoring_tab_actual_values().keySet()) {
                if (!elements2.containsKey(key) || !dq.get_support_monitoring_tab_actual_values().get(key).equals(elements2.get(key))) {
                    Assert.assertEquals(dq.get_support_monitoring_tab_actual_values().get(key),elements2.get(key));
                    System.out.println(elements2.get(key)+"-----"+dq.get_support_monitoring_tab_actual_values().get(key));
                }
                System.out.println(elements2.get(key)+"-----"+dq.get_support_monitoring_tab_actual_values().get(key));
                System.out.println("True");
            }
            test.pass("Verify Dates Test Verified Successfully");
            results.createNode("Verify Dates Test Verified Successfully");
            captureScreen();
            saveResult(ITestResult.SUCCESS, null);
        }catch (AssertionError er){
            test.fail("Verify Dates Test failed to verify");
            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("Verify Dates Test failed to verify");
            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }
    @Test(priority = 4)
    public void VerifyConfigurationTabData () throws InterruptedException, IOException {
        try {
            test = extent.createTest("AN-01: Verify that correct data is displayed in the Configuration Tab.");
            steps = test.createNode(Scenario.class, Steps);
            results = test.createNode(Scenario.class, Results);

            steps.createNode("1. Navigate to Dashboard Screen.");
            steps.createNode("2. Select Data Quality Tab.");
            steps.createNode("3. Verify that correct data is displayed in the Configuration Tab.");
            Data_Quality_Dashboard_Page dq = new Data_Quality_Dashboard_Page(driver);
            dq.click_configuration_tab();
            Thread.sleep(Duration.ofSeconds(5));
            Map<String, String> elements2 = new HashMap<String, String>() {{
                put("samplesNoCollectionDate", "12/1");
                put("programsNoEndDate", "51/1");
                put("programsWithEndDateOverYear", "51");
                put("complexMorethan1Program", "53");
                put("flocksNoSettlementDate", "3226");
                put("flocksMultiplePrograms", "3226");
                put("flocksMultiplePlans", "3226");
            }};
            for (String key : dq.get_configuration_tab_actual_values().keySet()) {
                if (!elements2.containsKey(key) || !dq.get_configuration_tab_actual_values().get(key).equals(elements2.get(key))) {
                    Assert.assertEquals(dq.get_configuration_tab_actual_values().get(key),elements2.get(key));
                    System.out.println(elements2.get(key)+"-----"+dq.get_configuration_tab_actual_values().get(key));
                }
                System.out.println(elements2.get(key)+"-----"+dq.get_configuration_tab_actual_values().get(key));
                System.out.println("True");
            }
            test.pass("Verify Dates Test Verified Successfully");
            results.createNode("Verify Dates Test Verified Successfully");
            captureScreen();
            saveResult(ITestResult.SUCCESS, null);
        }catch (AssertionError er){
            test.fail("Verify Dates Test failed to verify");
            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("Verify Dates Test failed to verify");
            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }
}
