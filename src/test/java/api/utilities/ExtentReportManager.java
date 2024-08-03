package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart (ITestContext testContext)
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //
		repName = "Test-Report-"+timeStamp+".html";
	
		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"/reports/"+repName);
		
//		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName);  // specify location of report
		
		sparkReporter.config().setDocumentTitle("Rest Assured Automation Report");  // title of report
		sparkReporter.config().setReportName("Rest API Testing Report");  //name of report
		sparkReporter.config().setTheme(Theme.DARK);
		
	    extent = new ExtentReports();
	    extent.attachReporter(sparkReporter);
	    extent.setSystemInfo("Application", "Pet store User APIs");
	    extent.setSystemInfo("Operating System", System.getProperty("os.name"));
	    extent.setSystemInfo("User Name", System.getProperty("user.name"));
	    extent.setSystemInfo("Environment", "QA");
	    extent.setSystemInfo("User", "Lokesh");
	}
	
	
	public void onTestSuccess(ITestResult result)
	{
	    test = extent.createTest(result.getName());
	    test.assignCategory(result.getMethod().getGroups());
	    test.createNode(result.getName());
	    test.log(Status.PASS, "Test Case is passed");
	}
	
	public void onTestFailure(ITestResult result)
	{
	    test = extent.createTest(result.getName());
	    test.createNode(result.getName());
	    test.assignCategory(result.getMethod().getGroups());
	    
	    test.log(Status.FAIL, "Test Case is Failed  -");
	    test.log(Status.FAIL, result.getThrowable());
	}
	
	public void onTestSkipped(ITestResult result)
	{
		
		test = extent.createTest(result.getName());
	    test.createNode(result.getName());
	    test.assignCategory(result.getMethod().getGroups());
	    
	    test.log(Status.SKIP, "Test Case is Skipped  -");
	    test.log(Status.SKIP, result.getThrowable().getMessage());
	 
	}
	public void onFinish(ITestContext testContext)
	{
	    extent.flush();
	}
}
