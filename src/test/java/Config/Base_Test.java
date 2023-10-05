
package Config;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.configuration.Theme;
import Misc.Constants;
import Misc.ExtentVariables;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class Base_Test {


    public static WebDriver driver;
    public static ReadPropertyFile config = ConfigFactory.create(ReadPropertyFile.class);


    @BeforeClass
    public void setup()
    {
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put( "profile.content_settings.pattern_pairs.*.multiple-automatic-downloads", 1 );
        prefs.put("download.prompt_for_download", false);
        prefs.put("profile.content_settings.exceptions.automatic_downloads.*.setting", 1 );
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-infobars");
        options.addArguments("disable-popup-blocking");
        options.addArguments("--remote-allow-origins=*");
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(Constants.LOGIN_URL);
        ExtentVariables.spark.config().setDocumentTitle("Acnera RP Test Report");
        ExtentVariables.spark.config().setTheme(Theme.STANDARD);
//        ExtentVariables.spark.config().setScreenshotPath("Theme.STANDARD");
        ExtentVariables.extent = new ExtentReports();
        ExtentVariables.extent.attachReporter(ExtentVariables.spark);
    }




    @AfterClass
    public void tearDown()
    {
        driver.quit();
    }

    public static String captureScreen() throws IOException {
        String dateName = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss").format(new Date());
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destination = Constants.ReportFilePath + Constants.ReportScreenshotPath+dateName+".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return "." + Constants.ReportScreenshotPath + dateName+".png";
    }
    public static void saveResult(int testResult, Exception e) throws IOException {
        Base_Test base = new Base_Test();
        ITestResult objResult = Reporter.getCurrentTestResult();
        if (testResult == ITestResult.SUCCESS) {
            objResult.setStatus(ITestResult.SUCCESS);
            ExtentVariables.test.log(Status.PASS, "Test Case Passed");
        } else if (testResult == ITestResult.FAILURE) {
            objResult.setStatus(ITestResult.FAILURE);
            objResult.setThrowable(e);
            ExtentVariables.test.log(Status.FAIL, "Test Case Failed");
            ExtentVariables.test.log(Status.FAIL, "Issue -> " + e);
            ExtentVariables.test.addScreenCaptureFromPath(base.captureScreen());
        } else if (testResult == ITestResult.SKIP) {
            ExtentVariables.test.log(Status.SKIP, "Test Case Skipped ");
            Markup m = MarkupHelper.createLabel("Skipped", ExtentColor.YELLOW);
            ExtentVariables.test.skip(m);
            ExtentVariables.test.addScreenCaptureFromPath(base.captureScreen());
        }
    }
    public String randomestring()
    {
        String generatedstring=RandomStringUtils.randomAlphabetic(8);
        return(generatedstring);
    }

    public static String randomeNum() {
        String generatedString2 = RandomStringUtils.randomNumeric(4);
        return (generatedString2);
    }


}
