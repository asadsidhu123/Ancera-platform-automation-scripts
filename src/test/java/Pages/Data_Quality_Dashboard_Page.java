package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.devtools.v85.css.model.Value;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class Data_Quality_Dashboard_Page {
    WebDriver ldriver;

    public Data_Quality_Dashboard_Page(WebDriver rdriver)
    {
        ldriver=rdriver;
        PageFactory.initElements(rdriver, this);
    }
    public static By pageHeading = By.xpath("//label[@class='sub-header-options-header']");
    public static By dataQualityTab = By.id("tabs-data-quality-tab");
    public static By supportMonitoringTab = By.id("tabs-support-monitoring-tab");
    public static By configurationTab = By.id("tabs-configuration-tab");
    public static By actualSelectedDate = By.xpath("//div[@class='custom-sub-container'][1]/p");

///////////////////////   Data Quality TAB    /////////////////////////////////////


    public static By nullCollectionDate = By.id("null-collection-date");
    public static By collectionsWithNo12Samples = By.id("num-of-samples");
    public static By noActiceFlocks = By.id("no-active-flock");
    public static By MissingCyclingConfiguration = By.id("missing-cycling-configuration");
    public static By CollectionDateOutOfRange = By.id("collection-date-out-of-range");
    public static By samplesNoAssociatedPrograms = By.id("samples-no-associated-programs");
    public static By flocksNoAssociatedPrograms = By.id("no-program-flocks");
    public static By moreThan65RockLength = By.id("rock-length");
    public static By cartridgeWithNo12Lanes = By.id("cartridge-not-have-12-lanes");
    public static By cartridgeWithLanesPending = By.id("lanes-pending-morethan-1-hr");
    public static By piperWithE03Errors = By.id("piper-with-morethan-5-e03");
    public static By numOfE06Errors = By.id("e06-errors");

//////////////////////    Support Monitoring TAB     ////////////////////////////////

    public static By cartridgeRunsOverErrors = By.id("total-cartridge-runs-over-errors");
    public static By sampleRunsOverErrors = By.id("total-samples-runs-over-errors");
    public static By samplesCollected = By.id("total-samples-collected");
    public static By activeFlocks = By.id("total-number-of-active-flocks");
    public static By collectionSites = By.id("total-collection-sites");

//////////////////////////    Configuration TAB    /////////////////////////////////////

    public static By samplesNoCollectionDate = By.id("samples-missing-collection-date");
    public static By programsNoEndDate = By.id("program-dont-have-end-date");
    public static By programsWithEndDateOverYear = By.id("programs-end-date-more-than-year");
    public static By complexMorethan1Program = By.id("morethan-one-active-program-complex");
    public static By flocksNoSettlementDate = By.id("flocks-missing-settlement-morethan-2-weeks");
    public static By flocksMultiplePrograms = By.id("flocks-with-multiple-programs");
    public static By flocksMultiplePlans = By.id("flocks-with-multiple-plans");


    public void click_support_monitoring_tab () throws InterruptedException {
        ldriver.findElement(supportMonitoringTab).click();
        Thread.sleep(Duration.ofSeconds(2));
    }
    public void click_configuration_tab () throws InterruptedException {
        ldriver.findElement(configurationTab).click();
        Thread.sleep(Duration.ofSeconds(2));
    }
    public Map<String, String>  get_data_quality_tab_actual_values (){
            Map<String, String> data = new HashMap<String, String>() {{
            put("nullCollectionDate", ldriver.findElement(nullCollectionDate).getText());
            put("collectionsWithNo12Samples", ldriver.findElement(collectionsWithNo12Samples).getText());
            put("noActiceFlocks", ldriver.findElement(noActiceFlocks).getText());
            put("MissingCyclingConfiguration", ldriver.findElement(MissingCyclingConfiguration).getText());
            put("CollectionDateOutOfRange", ldriver.findElement(CollectionDateOutOfRange).getText());
            put("samplesNoAssociatedPrograms", ldriver.findElement(samplesNoAssociatedPrograms).getText());
            put("flocksNoAssociatedPrograms", ldriver.findElement(flocksNoAssociatedPrograms).getText());
            put("moreThan65RockLength", ldriver.findElement(moreThan65RockLength).getText());
            put("cartridgeWithNo12Lanes", ldriver.findElement(cartridgeWithNo12Lanes).getText());
            put("cartridgeWithLanesPending", ldriver.findElement(cartridgeWithLanesPending).getText());
            put("piperWithE03Errors", ldriver.findElement(piperWithE03Errors).getText());
            put("numOfE06Errors", ldriver.findElement(numOfE06Errors).getText());
        }};
        return data;
    }
    public Map<String, String>  get_support_monitoring_tab_actual_values (){
            Map<String, String> data = new HashMap<String, String>() {{
            put("cartridgeRunsOverErrors", ldriver.findElement(cartridgeRunsOverErrors).getText());
            put("sampleRunsOverErrors", ldriver.findElement(sampleRunsOverErrors).getText());
            put("samplesCollected", ldriver.findElement(samplesCollected).getText());
            put("activeFlocks", ldriver.findElement(activeFlocks).getText());
            put("collectionSites", ldriver.findElement(collectionSites).getText());
        }};
        return data;
    }
    public Map<String, String>  get_configuration_tab_actual_values (){
            Map<String, String> data = new HashMap<String, String>() {{
            put("samplesNoCollectionDate", ldriver.findElement(samplesNoCollectionDate).getText());
            put("programsNoEndDate", ldriver.findElement(programsNoEndDate).getText());
            put("programsWithEndDateOverYear", ldriver.findElement(programsWithEndDateOverYear).getText());
            put("complexMorethan1Program", ldriver.findElement(complexMorethan1Program).getText());
            put("flocksNoSettlementDate", ldriver.findElement(flocksNoSettlementDate).getText());
            put("flocksMultiplePrograms", ldriver.findElement(flocksMultiplePrograms).getText());
            put("flocksMultiplePlans", ldriver.findElement(flocksMultiplePlans).getText());
        }};
        return data;
    }
    public String get_actual_selected_date (){
        return ldriver.findElement(actualSelectedDate).getText();
    }
}
