package Extentreportlistener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentReports getExtentReports() {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/ExtentSparkReport.html");
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("Test Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);


            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Tester", "Gyanraj");
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("OS Version", System.getProperty("os.version"));
            extent.setSystemInfo("Device", System.getenv("COMPUTERNAME"));
            

        }
        return extent;
    }


    public static ExtentTest createTest(String testName) {
        ExtentTest extentTest = getExtentReports().createTest(testName);
        test.set(extentTest);
        return extentTest;
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}

