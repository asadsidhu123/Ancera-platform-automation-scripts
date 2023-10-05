package Pages.SSM;

import Config.ReadPropertyFile;
import Misc.Functions.*;
import Pages.Side_Menu;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

import static Misc.Functions.*;

public class Overview_Page {
    static WebDriver ldriver;
    ReadPropertyFile config = ConfigFactory.create(ReadPropertyFile.class);

    public Overview_Page(WebDriver rdriver) {
        ldriver = rdriver;
        PageFactory.initElements(rdriver, this);
    }

    public static By pageHeading = By.xpath("//label[@class='sub-header-options-header']");
    public static By SelectedDate = By.xpath("[name()='p' and @class='clr-gray date-frame']");
    public static List<Map<String, Object>> gaugeChartExpectedData = new ArrayList<>();
    public static Map<String, Object> barChartExpectedData = new HashMap<>();

/////////////////////////////////GUAGE CHART//////////////////////////////////////////////////////////
    public static By gaugeChart = By.xpath("//*[@id = 'gauge-chart']");
    public static By gaugeChartRiskScore = By.xpath("//*[@id = 'gauge-chart']//*[name()='text']");
    public static By gaugeChartTrend = By.xpath("//*[@id = 'gauge']/img");
    public static By gaugeChartCircle = By.xpath("//*[@id = 'gauge-chart']//*[name()='circle'][2]");
    public static By gaugeChartToolTip = By.xpath("//*[@id='g-div']");
    public static By gaugeChartToolTipData = By.xpath("//*[@id='g-div']//*[name()='span' and @class='risk-score-tooltip-data']");

    /////////////////////////////////BAR CHART//////////////////////////////////////////////////////////

    public static By barChart = By.xpath("//*[@id = 'bar-chart']");
    public static By barChartBars = By.xpath("//*[@id = 'bar-chart']//*[name()='g'][1]//*[name()='rect']");
    public static By barChartLegends = By.xpath("//*[@id = 'bar-chart']//*[name()='g' and @class = 'legend']//*[name()='text']");
    public static By barChartToolTip = By.xpath("//*[@id='risk-timeline']//*[@id='tooltip']");
    public static By barChartToolTipData = By.xpath("//*[@id='risk-timeline']//*[@id='tooltip']//*[name() ='span']");

    /////////////////////////////////STACK CHART//////////////////////////////////////////////////////////

    public static By stackChart = By.xpath("//*[@id = 'stack-chart']");
    public static By stackChartLegends = By.xpath("//*[@id = 'stack-chart']//*[name()='g' and @class = 'legend']//*[name()='text']");
    public static By stackChartBars = By.xpath("//*[@id=\"stack-chart\"]//*[name()='g'][1]//*[name()='rect' and contains(@class, 'bar ') and @height != '0']");
    public static By stackChartToolTip = By.xpath("//*[@id='tooltip_stack']");
    public static By stackChartToolTipData = By.xpath("//*[@id='tooltip_stack']//*[name()='span' and contains(@class, 'm-l-10px')]");
    public static By stackChartToolTipDataRiskCategory = By.xpath("//*[@id='tooltip_stack']//*[name()='span' and @class = 'm-l-10px']");
    public static By stackChartToolTipDataBirdAge = By.xpath("//*[@id='tooltip_stack']//*[name()='span' and @class = 'm-l-10px category-clr']");
    public static By stackChartToolTipDataSerotype = By.xpath("//*[@id='tooltip_stack']//*[name()='ul']//*[name()='strong']");
    public static By stackChartToolTipDataHouse = By.xpath("//*[@id='tooltip_stack']//*[name()='ul']//*[name()='li']");
    public static By stackChartToolTipDataHousesAndSerotypes = By.xpath("//*[@id='tooltip_stack']//*[name()='ul']");

    /////////////////////////////////LINE CHART//////////////////////////////////////////////////////////

    public static By lineChart = By.xpath("//*[@id = 'line-chart']");
    public static By lineChartLegends = By.xpath("//*[@id = 'line-chart']//*[name()='g' and @class = 'legend']//*[name()='text']");
    public static By lineChartlines = By.xpath("//*[@id=\"line-chart\"]//*[name()='path' and contains(@class, 'line ')]");
    public static By lineChartToolTip = By.xpath("//*[@id='risk-timeline']//*[@id='tooltip']");
    public static By lineChartToolTipDataRiskCategory = By.xpath("//*[@id='tooltip']//*[name()='span']//*[name()='strong']");
    public static By lineChartToolTipDataPercentage = By.xpath("//*[@id='tooltip']//*[name()='span']//*[name()='span']");
    public static By lineChartToolTipDataDate = By.xpath("//*[@id='tooltip']//*[name()='span'][2]");
    public static By lineChartToolTipDataSerotypes = By.xpath("//*[@id='tooltip']//*[name()='li']");

    public static Map<String, Object> get_gauge_chart_actual_data(String option) throws InterruptedException {
        Side_Menu side_menu = new Side_Menu(ldriver);
        waitUntilInvisible(side_menu.loader);
        side_menu.select_date_range_option(option);
        waitUntilInvisible(side_menu.loader);
//        Thread.sleep(1000);
        Map<String, Object> data = new HashMap<>();
        data.put("Trend",ldriver.findElement(gaugeChartTrend).getAttribute("src").split("com")[1]);
        hover(ldriver.findElement(gaugeChartCircle),1,1);
        for (int i=0;i<ldriver.findElements(gaugeChartToolTipData).size();i++) {
            String [] attributeNames = {"Duration", "Risk Score", "Change in Score", "Highest"};
            Thread.sleep(500);
            data.put(attributeNames[i],ldriver.findElements(gaugeChartToolTipData).get(i).getText());
        }
        return data;
    }
    public static List<List<Map<String, Object>>> get_bar_chart_actual_data(String option) throws InterruptedException {
        Side_Menu side_menu = new Side_Menu(ldriver);
        waitUntilInvisible(side_menu.loader);
        side_menu.select_date_range_option(option);
        waitUntilInvisible(side_menu.loader);
        Thread.sleep(500);
        List<WebElement> riskCategories = ldriver.findElements(barChartLegends);
        List<WebElement> bars = ldriver.findElements(barChartBars);
        List<List<Map<String, Object>>> categoriesData = new ArrayList<>();
        for (WebElement riskCategory : riskCategories) {
            riskCategory.click();
            Thread.sleep(500);
            List<Map<String, Object>> barsData = new ArrayList<>();
            for (WebElement bar : bars) {
                String color = bar.getAttribute("fill");
                boolean isDisplayed = bar.isDisplayed();

                if ((color.equalsIgnoreCase("#E14456") || color.equalsIgnoreCase("#F37C53") || color.equalsIgnoreCase("#F8A745") || color.equalsIgnoreCase("#F2DD58")) && isDisplayed) {
                    int x = Math.round(bar.getSize().width / 30);
                    for (int i = 0; i < 2; i++) {
                        Map<String, Object> data = new HashMap<>(); // Create a new data map for each risk category
                        hover(bar, -4*i, 0);
                        if (ldriver.findElement(barChartToolTip).isDisplayed()) {
                            for (int j = 0; j < ldriver.findElements(barChartToolTipData).size(); j++) {
                                String[] attributeNames = {"Risk Category", "Date", "House and Serotype", "Maximum age at Risk"};
                                data.put(attributeNames[j], ldriver.findElements(barChartToolTipData).get(j).getText());
                            }
                        }
                        barsData.add(data);
                    }
                }
            }
            List<Map<String, Object>> filteredList = barsData.stream()
                    .filter(map -> !map.isEmpty())
                    .collect(Collectors.toList());
            categoriesData.add(filteredList);
        }
        return categoriesData;
    }

    public static List<List<Map<String, Object>>> get_stack_chart_actual_data(String option) throws InterruptedException {
        Side_Menu side_menu = new Side_Menu(ldriver);
        waitUntilInvisible(side_menu.loader);

        scrollUntilVisible(stackChart);

        // Create a list to store data for each legend
        List<List<Map<String, Object>>> actualData = new ArrayList<>();

        // Loop through all 4 legends (Critical KPI, Critical, High, Medium)
        for (int i = 0; i < ldriver.findElements(stackChartLegends).size(); i++) {
            ldriver.findElements(stackChartLegends).get(i).click(); // Clicking legends to only show their respective bars
            List<WebElement> bars = ldriver.findElements(stackChartBars); // Storing all the bars inside a list
            List<Map<String, Object>> legendBarsData = new ArrayList<>(); // Create a list to store data for bars within the legend

            if (bars.size() != 0) {
                for (WebElement bar : bars) { // Loop through the bars for the selected legend/category
                    Map<String, Object> data = new HashMap<>();
                    hover(bar, bar.getSize().width / 2, bar.getSize().height / 2); // Hovering on the bar to get the data
                    Thread.sleep(500);
                    data.put("Risk Category", ldriver.findElement(stackChartToolTipDataRiskCategory).getText());
                    data.put("Bird Age", ldriver.findElement(stackChartToolTipDataBirdAge).getText());
                    for (int j = 0; j < ldriver.findElements(stackChartToolTipDataSerotype).size(); j++) {
                        data.put("Serotype " + j, ldriver.findElements(stackChartToolTipDataHousesAndSerotypes).get(j).getText());
                    }
                    if (!ldriver.findElement(stackChartToolTip).isDisplayed()) {
                        outerLoop:
                        for (int x = 0; x < bar.getSize().width; x++) {
                            for (int y = 0; y < bar.getSize().height; y++) {
                                hover(bar, x, y);
                                if (ldriver.findElement(stackChartToolTip).isDisplayed()) {
                                    data.put("Risk Category", ldriver.findElement(stackChartToolTipDataRiskCategory).getText());
                                    data.put("Bird Age", ldriver.findElement(stackChartToolTipDataBirdAge).getText());
                                    for (int j = 0; j < ldriver.findElements(stackChartToolTipDataSerotype).size(); j++) {
                                        data.put("Serotype " + j, ldriver.findElements(stackChartToolTipDataHousesAndSerotypes).get(j).getText());
                                    }
                                    break outerLoop;
                                }
                            }
                        }
                    }
                    legendBarsData.add(data); // Add data for this bar to the legend's data list
                }
            }

            if (!legendBarsData.isEmpty()) {
                actualData.add(legendBarsData); // Add the legend's data list to the main data list if it's not empty
            }
        }

//        for (int i = 0; i < actualData.size(); i++) {
//            List<Map<String, Object>> legendBarsData = actualData.get(i);
//            System.out.println("Legend " + (i + 1) + " Data:");
//            System.out.println("----------------");
//            for (Map<String, Object> data : legendBarsData) {
//                for (Map.Entry<String, Object> entry : data.entrySet()) {
//                    System.out.println(entry.getKey() + " => " + entry.getValue());
//                }
//                System.out.println();
//            }
//        }

        return actualData;
    }


    public static List<List<Map<String, Object>>> get_line_chart_actual_data(String option) throws InterruptedException {
        Side_Menu side_menu = new Side_Menu(ldriver);
        waitUntilInvisible(side_menu.loader);
        //        side_menu.select_date_range_option(option);
        //        waitUntilInvisible(side_menu.loader);
        List<List<Map<String, Object>>> actualData = new ArrayList<>();
        for (int i = 0; i<ldriver.findElements(lineChartLegends).size(); i++) {
                ldriver.findElements(lineChartLegends).get(i).click();
                List<WebElement> lines = ldriver.findElements(lineChartlines);
                List<Map<String, Object>> legendlinesData = new ArrayList<>();
                if (lines.size() != 0) {
                    for (WebElement line : lines) {
//                        Map<String, Object> data = new HashMap<>();
                        if (line.isDisplayed()) {
                            int lineWidth = Math.round(line.getSize().width / 2);
                            int lineHeight = Math.round(line.getSize().height / 2);
                            int[] xOffsets = {-lineWidth + 2, 0, lineWidth - 4}; // Define the specific x-offsets
                            for (int xOffset : xOffsets) {
                                // Create a new data map for each xOffset
                                Map<String, Object> data = new HashMap<>();
                                boolean innerLoopBreak = false; // Flag to control the inner loop

                                for (int k = -lineHeight; k < lineHeight; k++) {
                                    hover(line, xOffset, k);
                                    if (ldriver.findElement(lineChartToolTip).isDisplayed()) {
                                        data.put("Risk Category", ldriver.findElement(lineChartToolTipDataRiskCategory).getText());
                                        data.put("Percentage", ldriver.findElement(lineChartToolTipDataPercentage).getText());
                                        data.put("Date", ldriver.findElement(lineChartToolTipDataDate).getText());

                                        // Fetch serotype data as needed
                                        List<WebElement> serotypeElements = ldriver.findElements(lineChartToolTipDataSerotypes);
                                        for (int j = 0; j < serotypeElements.size(); j++) {
                                            data.put("Serotype " + j, serotypeElements.get(j).getText());
                                            Thread.sleep(300);
                                        }
                                        innerLoopBreak = true;
                                        break; // Break out of the inner loop when tooltip is shown
                                    }
                                }

                                legendlinesData.add(data); // Add data for this xOffset to the legend's data list
                                legendlinesData.removeIf(Map::isEmpty);
                                if (!innerLoopBreak) {
                                    // If innerLoopBreak is still false, no tooltip was shown for this xOffset,
                                    // you can handle this case if needed
                                }

                            }
                        }
                    }
                }
            if (!legendlinesData.isEmpty()) {
                actualData.add(legendlinesData); // Add the legend's data list to the main data list if it's not empty
            }
        }
//        for (int i = 0; i < actualData.size(); i++) {
//            List<Map<String, Object>> legendlinesData = actualData.get(i);
//            System.out.println("Legend " + (i + 1) + " Data:");
//            System.out.println("----------------");
//            for (Map<String, Object> data : legendlinesData) {
//                for (Map.Entry<String, Object> entry : data.entrySet()) {
//                    System.out.println(entry.getKey() + " => " + entry.getValue());
//                }
//                System.out.println();
//            }
//        }
        return actualData;
        }
        public static List<Map<String, Object>> get_guage_chart_expected_data(){
            Map<String, Object> filter1 = new HashMap<>();
            filter1.put("Duration", "Last 4 Weeks");
            filter1.put("Risk Score", "44 moderate risk score");
            filter1.put("Change in Score", "+5 since 09/04/23");
            filter1.put("Highest", "Highest Risk Serotype: I 4,[5],12:i:");
            filter1.put("Trend", "/static/images/up_trend.svg");

            Map<String, Object> filter2 = new HashMap<>();
            filter2.put("Duration", "Last 3 Months");
            filter2.put("Risk Score", "44 moderate risk score");
            filter2.put("Change in Score", "+5 since 06/30/23");
            filter2.put("Highest", "Highest Risk Serotype: I 4,[5],12:i:");
            filter2.put("Trend", "/static/images/up_trend.svg");

            Map<String, Object> filter3 = new HashMap<>();
            filter3.put("Duration", "Last 12 Months");
            filter3.put("Risk Score", "44 moderate risk score");
            filter3.put("Change in Score", "+5 since 09/30/22");
            filter3.put("Highest", "Highest Risk Serotype: I 4,[5],12:i:");
            filter3.put("Trend", "/static/images/up_trend.svg");

            Map<String, Object> filter4 = new HashMap<>();
            filter4.put("Duration", "All Time");
            filter4.put("Risk Score", "44 moderate risk score");
            filter4.put("Change in Score", "+5 since 12/31/19");
            filter4.put("Highest", "Highest Risk Serotype: I 4,[5],12:i:");
            filter4.put("Trend", "/static/images/up_trend.svg");

            // Create an array to hold the classes
            gaugeChartExpectedData.add(filter1);
            gaugeChartExpectedData.add(filter2);
            gaugeChartExpectedData.add(filter3);
            gaugeChartExpectedData.add(filter4);
            return gaugeChartExpectedData;
        }
    public static List<List<Map<String, Object>>> get_bar_chart_expected_data(){

        List<List<Map<String, Object>>> ExpectedData = new ArrayList<>();
        List<Map<String, Object>> ExpectedDataByCategory1 = new ArrayList<>();
        List<Map<String, Object>> ExpectedDataByCategory2 = new ArrayList<>();
        List<Map<String, Object>> ExpectedDataByCategory3 = new ArrayList<>();
        List<Map<String, Object>> ExpectedDataByCategory4 = new ArrayList<>();

        Map<String, Object> CriticalKPIdata1 = new HashMap<>();
        CriticalKPIdata1.put("Risk Category", "Critical KPI");
        CriticalKPIdata1.put("Date", "Thursday, Sep 14, 2023");
        ExpectedDataByCategory1.add(CriticalKPIdata1);
        Map<String, Object> CriticalKPIdata2 = new HashMap<>();
        CriticalKPIdata2.put("Risk Category", "Critical KPI");
        CriticalKPIdata2.put("Date", "Tuesday, Sep 12, 2023");
        ExpectedDataByCategory1.add(CriticalKPIdata2);

        Map<String, Object> Criticaldata1 = new HashMap<>();
        Criticaldata1.put("Risk Category", "Critical");
        Criticaldata1.put("Date", "Thursday, Sep 21, 2023");
        ExpectedDataByCategory2.add(Criticaldata1);
        Map<String, Object> Criticaldata2 = new HashMap<>();
        Criticaldata2.put("Risk Category", "Critical");
        Criticaldata2.put("Date", "Tuesday, Sep 19, 2023");
//        Criticaldata2.put("House and Serotype", "1 house I 4,[5],12:i:");
//        Criticaldata2.put("Maximum age at Risk", "52 Weeks maximum age at risk");
        ExpectedDataByCategory2.add(Criticaldata2);

        Map<String, Object> Highdata1 = new HashMap<>();
        Highdata1.put("Risk Category", "High");
        Highdata1.put("Date", "Thursday, Sep 21, 2023");
        ExpectedDataByCategory3.add(Highdata1);
        Map<String, Object> Highdata2 = new HashMap<>();
        Highdata2.put("Risk Category", "High");
        Highdata2.put("Date", "Tuesday, Sep 19, 2023");
        ExpectedDataByCategory3.add(Highdata2);

        Map<String, Object> Mediumdata1 = new HashMap<>();
        Mediumdata1.put("Risk Category", "Medium");
        Mediumdata1.put("Date", "Thursday, Sep 21, 2023");
        ExpectedDataByCategory4.add(Mediumdata1);
        Map<String, Object> Mediumdata2 = new HashMap<>();
        Mediumdata2.put("Risk Category", "Medium");
        Mediumdata2.put("Date", "Tuesday, Sep 19, 2023");
//        Mediumdata2.put("House and Serotype", "1 house Altona II");
//        Mediumdata2.put("Maximum age at Risk", "52 Weeks maximum age at risk");
        ExpectedDataByCategory4.add(Mediumdata2);

        ExpectedData.add(ExpectedDataByCategory1);
        ExpectedData.add(ExpectedDataByCategory2);
        ExpectedData.add(ExpectedDataByCategory3);
        ExpectedData.add(ExpectedDataByCategory4);


        return ExpectedData;
    }
    public static List<List<Map<String, Object>>> get_stack_chart_expected_data(){

        // Create a List to store data for each legend
        List<List<Map<String, Object>>> ExpectedData = new ArrayList<>();

// Legend 1 - Critical
        List<Map<String, Object>> legend1Data = new ArrayList<>();
        Map<String, Object> legend1Bar1Data = new HashMap<>();
        legend1Bar1Data.put("Risk Category", "Critical");
        legend1Bar1Data.put("Serotype 0", "I 4,[5],12:i:\nJACKMAN AG, House 2");
        legend1Bar1Data.put("Bird Age", "30 Weeks Bird Age");
        legend1Data.add(legend1Bar1Data);

        Map<String, Object> legend1Bar2Data = new HashMap<>();
        legend1Bar2Data.put("Risk Category", "Critical");
        legend1Bar2Data.put("Serotype 0", "I 4,[5],12:i:\nCOBB HOLLOW, House 4");
        legend1Bar2Data.put("Serotype 1", "Javiana\nCOBB HOLLOW, House 2");
        legend1Bar2Data.put("Serotype 2", "Muenchen I\nCOBB HOLLOW, House 4");
        legend1Bar2Data.put("Bird Age", "50 Weeks Bird Age");
        legend1Data.add(legend1Bar2Data);

        ExpectedData.add(legend1Data);

// Legend 2 - High
        List<Map<String, Object>> legend2Data = new ArrayList<>();
        Map<String, Object> legend2Bar1Data = new HashMap<>();
        legend2Bar1Data.put("Risk Category", "High");
        legend2Bar1Data.put("Serotype 0", "Mbandaka I\nCHICOLATTA, House 4");
        legend2Bar1Data.put("Bird Age", "25 Weeks Bird Age");
        legend2Data.add(legend2Bar1Data);

        ExpectedData.add(legend2Data);

// Legend 3 - Medium
        List<Map<String, Object>> legend3Data = new ArrayList<>();
        Map<String, Object> legend3Bar1Data = new HashMap<>();
        legend3Bar1Data.put("Risk Category", "Medium");
        legend3Bar1Data.put("Serotype 0", "Kentucky I\nCHICOLATTA, House 4");
        legend3Bar1Data.put("Bird Age", "25 Weeks Bird Age");
        legend3Data.add(legend3Bar1Data);

        Map<String, Object> legend3Bar2Data = new HashMap<>();
        legend3Bar2Data.put("Risk Category", "Medium");
        legend3Bar2Data.put("Serotype 0", "Altona II\nJACKMAN AG, House 2");
        legend3Bar2Data.put("Serotype 1", "Kentucky I\nJACKMAN AG, House 2");
        legend3Bar2Data.put("Serotype 2", "Perth\nJACKMAN AG, House 2");
        legend3Bar2Data.put("Serotype 3", "Worthington II\nJACKMAN AG, House 2");
        legend3Bar2Data.put("Bird Age", "30 Weeks Bird Age");
        legend3Data.add(legend3Bar2Data);

        Map<String, Object> legend3Bar3Data = new HashMap<>();
        legend3Bar3Data.put("Risk Category", "Medium");
        legend3Bar3Data.put("Serotype 0", "Kentucky I\nBAR 7, House 3");
        legend3Bar3Data.put("Serotype 1", "Worthington II\nBAR 7, House 3");
        legend3Bar3Data.put("Bird Age", "40 Weeks Bird Age");
        legend3Data.add(legend3Bar3Data);

        Map<String, Object> legend3Bar4Data = new HashMap<>();
        legend3Bar4Data.put("Risk Category", "Medium");
        legend3Bar4Data.put("Serotype 0", "Adelaide\nCOBB HOLLOW, House 4");
        legend3Bar4Data.put("Serotype 1", "Baildon\nCOBB HOLLOW, House 4");
        legend3Bar4Data.put("Serotype 2", "Concord\nCOBB HOLLOW, House 4");
        legend3Bar4Data.put("Serotype 3", "Kentucky I\nCOBB HOLLOW, House 2");
        legend3Bar4Data.put("Serotype 4", "Newport I\nCOBB HOLLOW, House 4");
        legend3Bar4Data.put("Serotype 5", "Senftenberg\nCOBB HOLLOW, House 4");
        legend3Bar4Data.put("Bird Age", "50 Weeks Bird Age");
        legend3Data.add(legend3Bar4Data);

        ExpectedData.add(legend3Data);

        for (int i = 0; i < ExpectedData.size(); i++) {
            List<Map<String, Object>> legendBarsData = ExpectedData.get(i);
            System.out.println("Legend " + (i + 1) + " Data:");
            System.out.println("----------------");
            for (Map<String, Object> data : legendBarsData) {
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    System.out.println(entry.getKey() + " => " + entry.getValue());
                }
                System.out.println();
            }
        }

        return ExpectedData;
    }

    public static List<List<Map<String, Object>>> get_line_chart_expected_data(){
        List<List<Map<String, Object>>> ExpectedData = new ArrayList<>();

        // Legend 1 Data
//        List<Map<String, Object>> legend1Data = new ArrayList<>();
        // Add data for Legend 1 here if available
//        ExpectedData.add(legend1Data);

        // Legend 2 Data
        List<Map<String, Object>> legend2Data = new ArrayList<>();
        // Add data for Legend 2 here
        Map<String, Object> legend2Data1 = new HashMap<>();
        legend2Data1.put("Risk Category", "Critical");
        legend2Data1.put("Serotype 0", "Muenchen I");
        legend2Data1.put("Serotype 1", "I 4,[5],12:i:");
        legend2Data1.put("Serotype 2", "Javiana");
        legend2Data1.put("Percentage", "50%");
        legend2Data1.put("Date", "Monday, Sep 04, 2023");
        Map<String, Object> legend2Data2 = new HashMap<>();
        legend2Data2.put("Risk Category", "Critical");
        legend2Data2.put("Percentage", "0%");
        legend2Data2.put("Date", "Monday, Sep 11, 2023");
        Map<String, Object> legend2Data3 = new HashMap<>();
        legend2Data3.put("Risk Category", "Critical");
        legend2Data3.put("Serotype 0", "I 4,[5],12:i:");
        legend2Data3.put("Percentage", "12.5%");
        legend2Data3.put("Date", "Monday, Sep 18, 2023");
        // Add more data for Legend 2 if available
        legend2Data.add(legend2Data1);
        legend2Data.add(legend2Data2);
        legend2Data.add(legend2Data3);
        // Add additional entries to legend2Data as needed
        ExpectedData.add(legend2Data);

        // Legend 3 Data
        List<Map<String, Object>> legend3Data = new ArrayList<>();
        // Add data for Legend 3 here
        Map<String, Object> legend3Data1 = new HashMap<>();
        legend3Data1.put("Risk Category", "High");
        legend3Data1.put("Percentage", "0%");
        legend3Data1.put("Date", "Monday, Sep 04, 2023");
        Map<String, Object> legend3Data2 = new HashMap<>();
        legend3Data2.put("Risk Category", "High");
        legend3Data2.put("Serotype 0", "Mbandaka I");
        legend3Data2.put("Percentage", "12.5%");
        legend3Data2.put("Date", "Monday, Sep 11, 2023");
        Map<String, Object> legend3Data3 = new HashMap<>();
        legend3Data3.put("Risk Category", "High");
        legend3Data3.put("Percentage", "0%");
        legend3Data3.put("Date", "Monday, Sep 18, 2023");
        // Add more data for Legend 3 if available
        legend3Data.add(legend3Data1);
        legend3Data.add(legend3Data2);
        legend3Data.add(legend3Data3);
        // Add additional entries to legend3Data as needed
        ExpectedData.add(legend3Data);

        // Legend 4 Data
        List<Map<String, Object>> legend4Data = new ArrayList<>();
        // Add data for Legend 4 here
        Map<String, Object> legend4Data1 = new HashMap<>();
        legend4Data1.put("Risk Category", "Medium");
        legend4Data1.put("Serotype 0", "Kentucky I");
        legend4Data1.put("Serotype 1", "Senftenberg");
        legend4Data1.put("Serotype 2", "Newport I");
        legend4Data1.put("Serotype 3", "Concord");
        legend4Data1.put("Serotype 4", "");
        legend4Data1.put("Serotype 5", "");
        legend4Data1.put("Percentage", "50%");
        legend4Data1.put("Date", "Monday, Sep 04, 2023");
        Map<String, Object> legend4Data2 = new HashMap<>();
        legend4Data2.put("Risk Category", "Medium");
        legend4Data2.put("Serotype 0", "Kentucky I");
        legend4Data2.put("Serotype 1", "Kentucky I");
        legend4Data2.put("Serotype 2", "Worthington II");
        legend4Data2.put("Percentage", "25%");
        legend4Data2.put("Date", "Monday, Sep 11, 2023");
        Map<String, Object> legend4Data3 = new HashMap<>();
        legend4Data3.put("Risk Category", "Medium");
        legend4Data3.put("Serotype 0", "Worthington II");
        legend4Data3.put("Serotype 1", "Perth");
        legend4Data3.put("Serotype 2", "");
        legend4Data3.put("Serotype 3", "");
        legend4Data3.put("Percentage", "12.5%");
        legend4Data3.put("Date", "Monday, Sep 18, 2023");
        // Add more data for Legend 4 if available
        legend4Data.add(legend4Data1);
        legend4Data.add(legend4Data2);
        legend4Data.add(legend4Data3);
        // Add additional entries to legend4Data as needed
        ExpectedData.add(legend4Data);

        // Print allData
        for (int i = 0; i < ExpectedData.size(); i++) {
            System.out.println("Legend " + (i + 1) + " Data:");
            System.out.println("----------------");
            List<Map<String, Object>> legendData = ExpectedData.get(i);
            for (Map<String, Object> data : legendData) {
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    System.out.println(entry.getKey() + " => " + entry.getValue());
                }
                System.out.println();
            }
        }
            return ExpectedData;
        }

    public static boolean compareLists(List<Map<String, Object>> list1, List<Map<String, Object>> list2) {
        // Check if the lists have the same size
        if (list1.size() != list2.size()) {
            return false;
        }

        // Iterate through each Map in both lists
        for (int i = 0; i < list1.size(); i++) {
            Map<String, Object> map1 = list1.get(i);
            Map<String, Object> map2 = list2.get(i);
            System.out.println(map1 + "\n" + map2);
            Assert.assertEquals(map1,map2);
            // Compare the Maps
            if (!map1.equals(map2)) {
                return false;
            }
        }

        return true;
    }

    public static boolean are_lists_equal(List<List<Map<String, Object>>> list1, List<List<Map<String, Object>>> list2) {
        boolean areEqual = true;

        // Check if the lists have the same size
        if (list1.size() != list2.size()) {
            System.out.println("List Size differs: " + list1.size() + " vs " + list2.size());
            areEqual = false;
        }

        // Iterate through the lists
        for (int i = 0; i < list1.size(); i++) {
            List<Map<String, Object>> sublist1 = list1.get(i);
            List<Map<String, Object>> sublist2 = list2.get(i);
            if (sublist1.size() != sublist2.size()) {
                System.out.println("Sublist Size differs in List " + i + ": " + sublist1.size() + " vs " + sublist2.size());
                areEqual = false;
                continue; // Continue to the next sublist
            }

            // Iterate through the sublists
            for (int j = 0; j < sublist1.size(); j++) {
                Map<String, Object> map1 = sublist1.get(j);
                Map<String, Object> map2 = sublist2.get(j);

                // Check if the maps have the same size
                if (map1.size() != map2.size()) {
                    System.out.println("Map Size differs in List " + i + ", Sublist " + j);
                    areEqual = false;
                    continue; // Continue to the next map
                }

                // Iterate through the maps
                for (Map.Entry<String, Object> entry : map1.entrySet()) {
                    String key = entry.getKey();
                    Object value1 = entry.getValue();
                    Object value2 = map2.get(key);

                    // Compare the values for the same key
                    if (!value1.equals(value2)) {
                        System.out.println("Difference at List " + i + ", Sublist " + j + ", Key: " + key);
                        System.out.println("Value in List1: " + value1);
                        System.out.println("Value in List2: " + value2);
                        areEqual = false;
                    }
                }
            }
        }

        return areEqual;
    }


    public static boolean compareMaps(Map<String, Map<String, Object>> map1, Map<String, Map<String, Object>> map2) {
        // Check if the sizes of the maps are the same
        if (map1.size() != map2.size()) {
            return false;
        }

        // Iterate through map1 and compare each key-value pair
        for (Map.Entry<String, Map<String, Object>> entry1 : map1.entrySet()) {
            String key = entry1.getKey();
            Map<String, Object> value1 = entry1.getValue();

            // Check if map2 contains the same key
            if (!map2.containsKey(key)) {
                return false;
            }

            Map<String, Object> value2 = map2.get(key);

            // Check if the values associated with the key are equal
            if (!value1.equals(value2)) {
                return false;
            }
        }

        // If all key-value pairs match, the maps are considered equal
        return true;
    }
//    public static String[] get_stack_chart_bar_labels() throws InterruptedException {
//        Thread.sleep(2000);
//        scrollUntilVisible(stackChart);
//        Thread.sleep(1000);
//        List<WebElement> barLabels = ldriver.findElements(stackChartBarLabels);
//        String[] barLabelText = new String[barLabels.size()];
//        for (int i = 0; i < barLabels.size(); i++) {
//            barLabelText[i] = barLabels.get(i).getText();
////            System.out.println(barLabelText[i]);
//        }
//        Arrays.sort(barLabelText);
//        return barLabelText;
//
//    }
//
//    public static String[] get_stack_chart_bars_expected_weeks() {
//        List<WebElement> bars = ldriver.findElements(stackChartBars);
//        String[] barPositions = new String[ldriver.findElements(stackChartBars).size()];
//        int index = 0;
//        for (WebElement e : bars) {
//            if (e != null) {
//                switch (e.getAttribute("x")) {
//                    case "26.372549019607845":
//                        barPositions[index] = "25";
//                        break;
//                    case "140.09803921568627":
//                        barPo-sitions[index] = "30";
//                        break;
//                    case "253.8235294117647":
//                        barPositions[index] = "40";
//                        break;
//                    case "367.5490196078431":
//                        barPositions[index] = "50";
//                        break;
//                    case "481.27450980392155":
//                        barPositions[index] = "55";
//                        break;
//                }
//            }
////            System.out.println(barPositions[index]);
//            index++;
//        }
//        return barPositions;
//    }
//
//    public static String get_stack_chart_bars_actual_data() throws InterruptedException {
//        Side_Menu side_menu = new Side_Menu(driver);
//        Thread.sleep(5000);
//        waitUntilInvisible(side_menu.loader);
//        Thread.sleep(1000);
//        List<WebElement> bars = ldriver.findElements(stackChartBars);
//        String [] dataRiskCategory = new String[ldriver.findElements(stackChartBars).size()];
//        String [] dataBirdAge = new String[ldriver.findElements(stackChartBars).size()];
//        String [][] dataSerotypesNames = new String[ldriver.findElements(stackChartBars).size()][ldriver.findElements(stackChartToolTipSerotypes).size()];
//        for (int i = 0; i < bars.size(); i++) {
//            WebElement e =  ldriver.findElements(stackChartBars).get(i);
//            int elementWidth = e.getSize().getWidth();
//            int elementHeight = e.getSize().getHeight();
//            if (elementWidth != 0 && elementHeight !=0) {
//                int xOffset = elementWidth / 2;
//                int yOffset = elementHeight / 2 ;
////                System.out.println((elementWiddth.x+xOffset)+"-"+(elementWiddth.y+yOffset));
//                System.out.println(xOffset+"-"+yOffset);
//                hover(e,xOffset,yOffset);
//                Thread.sleep(1000);
//                String riskCategory  = ldriver.findElement(stackChartToolTipRiskCategory).getText();
//                System.out.println(riskCategory);
//                Thread.sleep(3000);
//            }
//        }
//        Thread.sleep(1000);
//        return "1";
//    }
//    public static String get_bar_chart_bars_actual_data() throws InterruptedException {
//        Side_Menu side_menu = new Side_Menu(driver);
//        Thread.sleep(5000);
//        waitUntilInvisible(side_menu.loader);
//        Thread.sleep(1000);
//        List<WebElement> bars = ldriver.findElements(barChartBars);
//        String [] dataRiskCategory = new String[ldriver.findElements(barChartBars).size()];
//        String [] dataDate = new String[ldriver.findElements(barChartBars).size()];
////        String [][] dataSerotypesNames = new String[ldriver.findElements(barChartBars).size()][ldriver.findElements(stackChartToolTipSerotypes).size()];
//        for (int i = 0; i < bars.size(); i++) {
//            WebElement e = ldriver.findElements(barChartBars).get(i);
//            int width = e.getSize().getWidth();
//            int height = e.getSize().getHeight();
//            String color = e.getAttribute("fill");
//            if (width != 0 && height != 0) {
//                if (color.equals("#F2DD58") || color.equals("#F8A745") || color.equals("#F37C53") || color.equals("#E14456")) {
//                    System.out.println(color + " - " + width + " - " + height);
//                    int xOffset = width - width + 1;
//                    int yOffset = height / 2;
//                    hover(e, xOffset, yOffset);
//                    Thread.sleep(1000);
//                    String riskCategory = ldriver.findElement(barChartToolTipRiskCategory).getText();
//                    String date = ldriver.findElement(barChartToolTipDate).getText();
//                    System.out.println(riskCategory + " - " + date);
//                    Thread.sleep(3000);
//                }
//            }
//        }
//        Thread.sleep(1000);
//        return "1";
//    }
//    public static String get_bar_chart_bars_actual_data() throws InterruptedException {
//        Side_Menu side_menu = new Side_Menu(driver);
//        Thread.sleep(3000);
//        waitUntilInvisible(side_menu.loader);
//        Thread.sleep(1000);
//        scrollUntilVisible(By.xpath("//*[@id = 'line-chart']"));
//        Thread.sleep(1000);
//        List<WebElement> bars = ldriver.findElements(By.xpath("//*[@id = 'line-chart']//*[name()='path' and  contains(@class, 'line ')]"));
//        String [] dataRiskCategory = new String[ldriver.findElements(By.xpath("//*[@id = 'line-chart']//*[name()='path' and  contains(@class, 'line ')]")).size()];
//        String [] dataDate = new String[ldriver.findElements(By.xpath("//*[@id = 'line-chart']//*[name()='path' and  contains(@class, 'line ')]")).size()];
////        String [][] dataSerotypesNames = new String[ldriver.findElements(barChartBars).size()][ldriver.findElements(stackChartToolTipSerotypes).size()];
//
//        WebElement element = ldriver.findElement(By.xpath("//*[@id = 'line-chart']//*[name()='path' and  contains(@class, 'line Critical-2')]"));
//        ldriver.findElement(By.xpath("//*[@id = 'line-chart']//*[name()='text' and text() = 'Critical']")).click();
//        Thread.sleep(1000);
//
//
//        int height = element.getSize().getHeight();
//        int width = Math.round(element.getSize().getWidth() / 2);
//        System.out.println(height);
//        System.out.println(width);
//        for (int i = height-20; i <height;i++) {
//            for (int j = -width; j <-width+10 ; j++){
//                hover(element,j, i);
////            Thread.sleep(200);
//                if (ldriver.findElement(By.xpath("//*[@id = 'tooltip']")).isDisplayed()) {
//                    System.out.println(i);
//                    String riskCategory = ldriver.findElement(By.xpath("//*[@id = 'tooltip']//*[name()='strong'][1]")).getText();
//                    System.out.println(riskCategory);
//                    break;
//                }
//
//            }
//        }
//        Thread.sleep(1000);
//        return "1";
//    }
//

}
