package automation.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.internal.BaseTestMethod;

import automation.TestSessionInitiator;

public class Parent_Test {
	
	public TestSessionInitiator test;

	public static void afterMethod(TestSessionInitiator test, ITestResult result, String testName) {
		String testcaseName = result.getName();
		Reporter.log("*************************************", true);
		Reporter.log("Test Name: " + testcaseName, true);
		Reporter.log("Test time in secs: " + ((result.getEndMillis() - result.getStartMillis()) / 1000), true);
		print_Status(result.getStatus(), result);
		if (result.getStatus() == ITestResult.FAILURE) {
			print_Status(result.getStatus(), result);
		}
		Reporter.log("*************************************", true);
	}

	public static String getMethodName(ITestResult result, String parameter) {
		parameter = parameter.replaceAll("\\s", "");
		parameter = parameter.replaceAll(",", "");

		String testcaseName = result.getName() + "-For-" + parameter;
		try {
			BaseTestMethod bm = (BaseTestMethod) result.getMethod();
			Field f = bm.getClass().getSuperclass().getDeclaredField("m_methodName");
			f.setAccessible(true);
			f.set(bm, testcaseName);
			System.out.println("*************************************");
			System.out.println("Test Name: " + result.getName());
		} catch (Exception ex) {
			Reporter.log("ex" + ex.getMessage());
		}
		return testcaseName;
	}

	public static void print_Status(int results, ITestResult result) {
		switch (results) {
		case 2:
			Reporter.log("Test status :- FAIL", true);
			break;
		case 1:
			Reporter.log("Test status :- PASS", true);
			break;
		case 3:
			Reporter.log("Test status :- SKIP", true);
			break;
		default:
			break;
		}
	}

	public void printClassName(String className) {
		Reporter.log("*************************************\nStarting code for :- " + className + "\n"
				+ "*************************************", true);

	}
	
	public String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
	    Date date = new Date();  
	    System.out.println(formatter.format(date));  
	    return formatter.format(date);
	}
	
	@BeforeClass
	public void start_test_Session() {
		test = new TestSessionInitiator();
	}

	@BeforeMethod
	public void handleTestMethodName(Method method) {
		test.stepStartMessage(this.getClass().getSimpleName() + " : " + method.getName());
	}
	
	@AfterClass
	public void stop_test_session() throws InterruptedException {
		test.closeBrowserSession();
	}
	
	@AfterMethod
	public void onFailure(ITestResult result) {
		afterMethod(test, result, this.getClass().getName());
	}
}
