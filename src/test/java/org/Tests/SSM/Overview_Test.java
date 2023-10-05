package org.Tests.SSM;

import Config.Base_Test;
import Misc.DateUtil;
import Pages.SSM.Overview_Page;
import com.aventstack.extentreports.gherkin.model.Scenario;
import com.aventstack.extentreports.observer.entity.MediaEntity;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.Tests.Login_Test;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Misc.ExtentVariables.*;

public class Overview_Test extends Base_Test {
    public static  File img;
    @BeforeTest
    public void extent() throws InterruptedException, IOException {
        spark = new ExtentSparkReporter("target/Reports/SSM/Overview/Overview_"+ DateUtil.date+".html");
        spark.config().setReportName("SSM Overview Test Report");
    }
    @BeforeClass
    public void Login() throws InterruptedException, IOException {
        Login_Test.Login();
    }

///////////////////////////////////////////////////COMPLETE//////////////////////////////////////////////////////////

    @Test(priority = 1)
    public void VerifyGaugeChart () throws InterruptedException, IOException {
        try {
            test = extent.createTest("Verify that correct data is showing on the Guage Chart.");
            preconditions = test.createNode(Scenario.class,PreConditions);
            steps = test.createNode(Scenario.class, Steps);
            preconditions.createNode("1. Login to the Reporting Platform");
            preconditions.createNode("2. Navigate to SSM Salmonella Risk Overview Screen.");
//            results = test.createNode(Scenario.class, Results);
            Thread.sleep(Duration.ofSeconds(1));
            Overview_Page overview_page = new Overview_Page(driver);

            steps.createNode("1. Gather all the data from the Guage Chart against Last 4 Weeks filters.");
            Map<String, Object> actualData4Weeks = overview_page.get_gauge_chart_actual_data( "Last 4 Weeks");
            steps.createNode("2. Gather all the data from the Guage Chart against Last 3 Months filters.");
            Map<String, Object> actualData3Months = overview_page.get_gauge_chart_actual_data( "Last 3 Months");
            steps.createNode("3. Gather all the data from the Guage Chart against Last 12 Months filters.");
            Map<String, Object> actualData12Months = overview_page.get_gauge_chart_actual_data( "Last 12 Months");
            steps.createNode("4. Gather all the data from the Guage Chart against All Time filters.");
            Map<String, Object> actualDataAllTime = overview_page.get_gauge_chart_actual_data( "All Time");
            steps.createNode("5. Make a list for all the gathered data from the guage chart against all filters.");
            List<Map<String, Object>> ActualData1 = new ArrayList<>();
            ActualData1.add(actualData4Weeks);
            ActualData1.add(actualData3Months);
            ActualData1.add(actualData12Months);
            ActualData1.add(actualDataAllTime);
            List<List<Map<String, Object>>> ActualData = new ArrayList<>();
            ActualData.add(ActualData1);

            steps.createNode("6. Gather all the expeted data for the Guage Chart against all filters and make a list of it.");
            List<Map<String, Object>> ExpectedData1 = overview_page.get_guage_chart_expected_data();
            List<List<Map<String, Object>>> ExpectedData = new ArrayList<>();
            ExpectedData.add(ExpectedData1);
            Assert.assertTrue(overview_page.are_lists_equal(ActualData, ExpectedData));
            steps.createNode("7. Verify that all the gathered actual data is the same as all the expected data.");
            test.pass("Correct data is showing on the Guage Chart.");
//            File img = captureScreen();
//            results.createNode("Test Case Verified Successfully");
            saveResult(ITestResult.SUCCESS, null);

        }catch (AssertionError er){
            test.fail("Correct data is not showing on the Guage Chart.");
//            results.createNode("Test Case failed to verify");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("Correct data is not showing on the Guage Chart.");
//            results.createNode("Test Case failed to verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }

///////////////////////////////////////////////////COMPLETE//////////////////////////////////////////////////////////

    @Test(priority = 2)
    public void VerifyBarChart () throws InterruptedException, IOException {
        try {
            test = extent.createTest("Verify that correct data is showing on the Bar Chart.");
            steps = test.createNode(Scenario.class, Steps);

//            results = test.createNode(Scenario.class, Results);
            Thread.sleep(Duration.ofSeconds(1));

            Overview_Page overview_page = new Overview_Page(driver);
            List<List<Map<String, Object>>> ExpectedData = overview_page.get_bar_chart_expected_data();
            steps.createNode("1. Gather all the data from the Bar Chart against Last 4 Weeks filters.");
            List<List<Map<String, Object>>> ActualData4Weeks = overview_page.get_bar_chart_actual_data("Last 4 Weeks");
            steps.createNode("2. Gather all the data from the Bar Chart against Last 3 Months filters.");
            List<List<Map<String, Object>>> ActualData3Months = overview_page.get_bar_chart_actual_data("Last 3 Months");
            steps.createNode("3. Gather all the data from the Bar Chart against Last 12 Months filters.");
            List<List<Map<String, Object>>> ActualData12Months = overview_page.get_bar_chart_actual_data("Last 12 Months");
            steps.createNode("4. Gather all the data from the Bar Chart against All Time filters.");
            List<List<Map<String, Object>>> ActualDataAllTime = overview_page.get_bar_chart_actual_data("All Time");

            steps.createNode("5. Verify that all the gathered actual data is the same as all the expected data.");
            Assert.assertTrue(overview_page.are_lists_equal(ActualData4Weeks, ExpectedData));
            Assert.assertTrue(overview_page.are_lists_equal(ActualData3Months, ExpectedData));
            Assert.assertTrue(overview_page.are_lists_equal(ActualData12Months, ExpectedData));
            Assert.assertTrue(overview_page.are_lists_equal(ActualDataAllTime, ExpectedData));
            test.pass("Correct data is showing on the Bar Chart.");
            captureScreen();
//            results.createNode("Test Case Verified Successfully");
            saveResult(ITestResult.SUCCESS, null);

        }catch (AssertionError er){
            test.fail("Correct data is not showing on the Bar Chart.");
//            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("Correct data is not showing on the Bar Chart.");
//            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }

    ///////////////////////////////////////////////////COMPLETE//////////////////////////////////////////////////////////

        @Test(priority = 3)
    public void VerifyStackChart () throws InterruptedException, IOException {
        try {
            test = extent.createTest("Verify that correct data is showing on the Stack Chart.");
            steps = test.createNode(Scenario.class, Steps);

//            results = test.createNode(Scenario.class, Results);
            Thread.sleep(Duration.ofSeconds(1));
            Overview_Page overview_page = new Overview_Page(driver);
            List<List<Map<String, Object>>> ExpectedData = overview_page.get_stack_chart_expected_data();
            steps.createNode("1. Gather all the data from the Stack Chart against applied Filter.");
            List<List<Map<String, Object>>> ActualData4Weeks = overview_page.get_stack_chart_actual_data("Last 4 Weeks");
            steps.createNode("2. Verify that all the gathered actual data is the same as all the expected data.");
            Assert.assertTrue(overview_page.are_lists_equal(ActualData4Weeks, ExpectedData));
            test.pass("Correct data is showing on the Stack Chart.");
            captureScreen();
//            results.createNode("Test Case Verified Successfully");
            saveResult(ITestResult.SUCCESS, null);

        }catch (AssertionError er){
            test.fail("Correct data is not showing on the Stack Chart.");
//            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("Correct data is not showing on the Stack Chart.");
//            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }

    ///////////////////////////////////////////////////COMPLETE//////////////////////////////////////////////////////////

        @Test(priority = 4)
    public void VerifyLineChart () throws InterruptedException, IOException {
        try {
            test = extent.createTest("Verify that correct data is showing on the Line Chart.");
            steps = test.createNode(Scenario.class, Steps);

//            results = test.createNode(Scenario.class, Results);
            Thread.sleep(Duration.ofSeconds(1));
            Overview_Page overview_page = new Overview_Page(driver);
            List<List<Map<String, Object>>> ExpectedData = overview_page.get_line_chart_expected_data();
            steps.createNode("1. Gather all the data from the Line Chart against applied Filter.");
            List<List<Map<String, Object>>> ActualData4Weeks = overview_page.get_line_chart_actual_data("Last 4 Weeks");
            steps.createNode("2. Verify that all the gathered actual data is the same as all the expected data.");
            Assert.assertTrue(overview_page.are_lists_equal(ActualData4Weeks, ExpectedData));
            test.pass("Correct data is showing on the Line Chart.");
            captureScreen();
//            results.createNode("Test Case Verified Successfully");
            saveResult(ITestResult.SUCCESS, null);
        }catch (AssertionError er){
            test.fail("Correct data is not showing on the Line Chart.");
//            results.createNode("Verify Dates Test failed to verify");
           saveResult(ITestResult.FAILURE,  new Exception(er));
        }
        catch(Exception ex){
            test.fail("Correct data is not showing on the Line Chart.");
//            results.createNode("Verify Dates Test failed to verify");
            saveResult(ITestResult.FAILURE,  ex);
        }
    }

//    @Test(priority = 2)
//    public void VerifyStackChartTooltipData () throws InterruptedException, IOException {
//        try {
//            Thread.sleep(Duration.ofSeconds(5));
//            Overview_Page overview_page = new Overview_Page(driver);
////            String [] expectedBarLabels = {"1","1","1","2","3","4","7"};
////            String [] stackChartBarExpectedWeeks = overview_page.get_stack_chart_bars_expected_weeks();
//            String criticalKPIActualWeeks = overview_page.get_stack_chart_bars_actual_data();
//
//        }catch (AssertionError er){
//            test.fail("Verify Dates Test failed to verify");
//            results.createNode("Verify Dates Test failed to verify");
//            saveResult(ITestResult.FAILURE,  new Exception(er));
//        }
//        catch(Exception ex){
//            test.fail("Verify Dates Test failed to verify");
//            results.createNode("Verify Dates Test failed to verify");
//            saveResult(ITestResult.FAILURE,  ex);
//        }
//    }

    @AfterTest
    public static void endreport() {
        extent.flush();
    }

}
