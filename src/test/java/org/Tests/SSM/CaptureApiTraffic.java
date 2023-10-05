package org.Tests.SSM;

import Config.Base_Test;
import Misc.Constants;
import Misc.DateUtil;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.Tests.Login_Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v115.network.Network;
import org.openqa.selenium.devtools.v115.network.model.Request;

import org.openqa.selenium.devtools.v115.network.model.RequestId;
import org.openqa.selenium.devtools.v115.network.model.Response;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Optional;

import static Misc.ExtentVariables.*;

public class CaptureApiTraffic extends Base_Test {
    @BeforeTest
    public void extent() throws InterruptedException, IOException {
        spark = new ExtentSparkReporter("target/Reports/SSM/Overview/Overview_"+ DateUtil.date+".html");
        spark.config().setReportName("SSM Overview Test Report");
    }
    @BeforeClass
    public void Login() throws InterruptedException, IOException {
        Login_Test.Login();
    }

    @Test
    public void captureApiTrafficTest() throws InterruptedException {

        DevTools devTools =  ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(),Optional.empty(),Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(),requestConsumer ->{
            Request request = requestConsumer.getRequest();
//            System.out.println(request.getUrl()+"OYE");
        });

        final RequestId[] requestId = new RequestId[1];

        devTools.addListener(Network.responseReceived(),responseConsumer ->{
            Response response = responseConsumer.getResponse();
            if (response.getUrl().contains("https://reporting-qa.ancera.com/home/ssm/overview_chart_data/")){
//                requestId[0] = responseConsumer.getRequestId();
                System.out.println(response.getUrl());

            }
        });
        driver.get("https://reporting-qa.ancera.com/home/ssm/overview/");
    }

    @AfterTest
    public static void endreport() {

        extent.flush();
    }

}
