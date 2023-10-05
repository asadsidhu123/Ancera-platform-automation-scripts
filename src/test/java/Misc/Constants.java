package Misc;

import Config.ReadPropertyFile;
import org.aeonbits.owner.ConfigFactory;

public class Constants {
    public static ReadPropertyFile config = ConfigFactory.create(ReadPropertyFile.class);
    public static String LOGIN_URL = "https://reporting-qa.ancera.com/home/ssm/overview/";
//    public static String LOGIN_URL = config.url()+"/auth/signin";
    public static String DASHBOARD_URL = config.url()+"/home/";
    public static String FORGOT_PASSWORD_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/requestPasswordResetCode";
//    public static String GMAIL_EMAIL = "as2260223667@gmail.com";

    public static String ReportFilePath = "./target/Reports";
    public static String ReportScreenshotPath = "/ScreenShots/ScreenShot";
    public static String OUTLOOK_LOGIN_URL = "https://login.live.com/login.srf?wa=wsignin1.0&rpsnv=16&ct=1693361819&rver=7.3.6960.0&wp=SAPI&wreply=https%3A%2F%2Faccount.live.com%2FSummaryPage.aspx%3Fuaid%3D16a4f51546134db4a45ecd6cd606803c%26lc%3D1033&lc=1033&id=38936&mkt=en-US&uaid=16a4f51546134db4a45ecd6cd606803c";

    public static String fp_email = "as2260223667@hotmail.com";
    public static String fp_password = "Ancer@12345678";
    public static String login_email = "asad.sidhu@tenx.ai";
    public static String login_password = "asadqa12345";

}
