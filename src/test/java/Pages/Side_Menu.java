package Pages;


import Misc.Functions;
import Misc.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.time.Year;

public class Side_Menu

{
    WebDriver ldriver;

    public Side_Menu(WebDriver rdriver)
    {
        ldriver=rdriver;
        PageFactory.initElements(rdriver, this);
    }

    public static By loader = By.xpath("//*[@id = 'loader']//*[name()='img']");
    public static By greeting = By.xpath("//*[@id=\"header\"]/h3");
//    public static By logoutBtn = By.id("log_out_opt");
    public static By logoutBtn = By.xpath("//*[@id = 'log_out_opt']//*[name()='img']");
    public static By profileSettingsBtn = By.id("profile_settings_opt_img");
    public static By logoutConfirm = By.id("btn-confirm");
    public static By dataQualityDashboardNav = By.id("data_quality_support_opt");
    public static By myReportsNav = By.id("data_quality_support_opt");

    public static By dateRangeSelector = By.id("date-range-selector");
    public static By dateField = By.xpath("//input[contains(@class, 'date-range-picker-alt-input')][1]");
    public static By resetFilterBrn = By.id("reset-filter");
    public static By monthDropdown = By.xpath("//select[@aria-label='Month']");
    public static By yearField = By.xpath("//input[@aria-label='Year']");
    public static By startDate = By.xpath("//div[@class='dayContainer']/span[8]");
    public static By endDate = By.xpath("//div[@class='dayContainer']/span[14]");
    public static By actualSelectedDate = By.xpath("//div[@class='custom-sub-container'][1]/p");

    public void click_logout (){
        JavascriptExecutor jse = (JavascriptExecutor)ldriver;
        jse.executeScript("arguments[0].scrollIntoView()", ldriver.findElement(logoutBtn));
        ldriver.findElement(logoutBtn).click();
    }
    public void click_profile_settings (){
        JavascriptExecutor jse = (JavascriptExecutor)ldriver;
        jse.executeScript("arguments[0].scrollIntoView()", ldriver.findElement(profileSettingsBtn));
       ldriver.findElement(profileSettingsBtn).click();
    }
    public void confirm_logout (){
        ldriver.findElement(logoutConfirm).click();
    }
    public void click_data_quality_dashboard (){
        JavascriptExecutor jse = (JavascriptExecutor)ldriver;
        jse.executeScript("arguments[0].scrollIntoView()", ldriver.findElement(dataQualityDashboardNav));
        ldriver.findElement(dataQualityDashboardNav).click();
    }
    public void click_my_reports (){
        JavascriptExecutor jse = (JavascriptExecutor)ldriver;
        jse.executeScript("arguments[0].scrollIntoView()", myReportsNav);
        ldriver.findElement(myReportsNav).click();
    }
    public void select_date_range_option (String option){
        Select dateRangeDropdown = new Select(ldriver.findElement(dateRangeSelector));
        dateRangeDropdown.selectByVisibleText(option);
    }
    public void click_date_field () throws InterruptedException {
        ldriver.findElement(dateField).click();
        Thread.sleep(Duration.ofSeconds(2));
    }
    public void select_start_date () throws InterruptedException {
        ldriver.findElement(startDate).click();
        Thread.sleep(Duration.ofSeconds(2));
    }
    public void select_end_date () throws InterruptedException {
        ldriver.findElement(endDate).click();
        Thread.sleep(Duration.ofSeconds(2));
    }
    public String get_expected_selected_date () throws InterruptedException {
        String expectedDate = null;
        String monthExpected = new Select(ldriver.findElement(monthDropdown)).getFirstSelectedOption().getText().substring(0,3);
        String yearExpected = Integer.toString(Year.now().getValue());
        String startDateExpected = ldriver.findElement(startDate).getText();
        String endDateExpected = ldriver.findElement(endDate).getText();
        expectedDate = monthExpected+" "+startDateExpected+", "+yearExpected+" - "+monthExpected+" "+endDateExpected+", "+yearExpected;
        return expectedDate;
    }


}
