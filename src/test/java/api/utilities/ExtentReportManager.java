package api.utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class ExtentReportManager implements ITestListener {
	
	ExtentReports extReports;
	public static ExtentTest  test;
	String realtime;
	
	public void onStart(ITestContext context) 
	{
		LocalDateTime time =LocalDateTime.now();
		DateTimeFormatter customformat =DateTimeFormatter.ofPattern("dd-MM-yyyy HH_mm_ss a");
		realtime =time.format(customformat);
		  
		extReports = new ExtentReports();
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(".\\reports\\Test-Reports_"+realtime+".html");
		
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setDocumentTitle("RestAssured Petstore Automation");
		sparkReporter.config().setReportName("Pet Store API Test Report");
		sparkReporter.config().setTimeStampFormat("dd/MM/yyyy hh:mm:ss a");
	
		extReports.attachReporter(sparkReporter);
	
		extReports.setSystemInfo("Application", "Pet Store Users Api");
//		extReports.setSystemInfo("Browser Name",System.getProperty("browser.name"));
//		extReports.setSystemInfo("Operating System", System.getProperty("os.name"));
		extReports.setSystemInfo("User", "Krushna");
		extReports.setSystemInfo("Environment", "QA");
	}
	
	
	public void onTestStart(ITestResult result) 
	{
		System.out.println("************************************");
		System.out.println("************************************");
		System.out.println();
		System.out.println();

		System.out.println(result.getName() + " started executing.");
		
		test = extReports.createTest(result.getTestClass().getName() +" - "+
				result.getMethod().getPriority() + ". " + result.getMethod().getMethodName(), result.getMethod().getDescription());
		test.log(Status.INFO, result.getName() + " started executing.");
	}

	
	public void onTestSuccess(ITestResult result) 
	{
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName() + " got successfully executed.");
		
		System.out.println(result.getName() + " got successfully executed.");
	}

	
	public void onTestFailure(ITestResult result) 
	{
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.INFO, result.getThrowable());
		test.log(Status.FAIL, result.getName() + " got failed.");
		
		System.out.println(result.getName() + " got failed.");
	}

	
	public void onTestSkipped(ITestResult result) 
	{
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.INFO, result.getThrowable());
		test.log(Status.SKIP, result.getName() + " got skipped.");
		
		System.out.println(result.getName() + " got skipped.");
	}

	
	public void onFinish(ITestContext context) 
	{
		extReports.flush();
		String extentReportPath = "F:\\WorkSpace\\RestAssuredPetStoreAutomation-main\\reports\\Test-Reports_"+realtime+".html";
		File extentReportCopy = new File(extentReportPath);
		try {
			Desktop.getDesktop().browse(extentReportCopy.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		
}






