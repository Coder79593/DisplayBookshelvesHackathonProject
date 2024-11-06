package Extentreportlistener;
/*
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
 
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
 
public class ExtentReportManager implements ITestListener,ISuiteListener{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	String repName;
	public void onStart(ISuite suite) {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());// time stamp
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\Reports\\" + repName);// specify location of the report
		sparkReporter.config().setDocumentTitle("EMI Calculator Automation Report"); // Title of report
		sparkReporter.config().setReportName("EMI Calculator Functional Testing"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "EMI Calculator");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environemnt", "QA");
		extent.setSystemInfo("Operating System", "Windows");
		extent.setSystemInfo("Browser", "Chrome, Edge");
    }

   public void onFinish(ISuite suite) {
        extent.flush();
        String pathOfExtentReport = System.getProperty("user.dir") + "\\Reports\\"+repName;
        File extentReport = new File(pathOfExtentReport);
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   public void onStart(ITestContext testContext) {
   }
	public void onTestStart(ITestResult result) {

		String os = result.getTestContext().getCurrentXmlTest().getParameter("os");
		//extent.setSystemInfo("Operating System", os);
		String browser = result.getTestContext().getCurrentXmlTest().getParameter("browser");
		//extent.setSystemInfo("Browser", browser);
		List<String> includedGroups = result.getTestContext().getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
		extent.setSystemInfo("Groups", includedGroups.toString());
		}
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.assignCategory(browser);
		test.assignCategory(os);
		test.log(Status.INFO,"Browser : "+browser);

	}
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS,result.getName()+" got successfully executed");
	}
	public void onTestSkipped(ITestResult result) {
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	public void onFinish(ITestContext testContext) {

	}
}
*/


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import drivers.BaseClass;

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
            String browserName = BaseClass.getProperty("browser");
		    extent.setSystemInfo("Browser", browserName);
            

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

