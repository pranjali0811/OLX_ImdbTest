/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automation;

import static automation.utils.DataReadWrite.getProperty;

import java.util.List;
import java.util.Scanner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import automation.utils.Msg;
import automation.utils.SeleniumWait;

public class BaseUi {

	WebDriver driver, driverToUploadImage;
	public SeleniumWait wait;
	private String pageName;
	public Msg msg;
	protected String browser;
	List<String> temp;
	String str;
	String[] words;
	WebElement element;
	Scanner sc = new Scanner(System.in);
	JavascriptExecutor js = (JavascriptExecutor) driver;

	protected BaseUi(WebDriver driver, String pageName) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		this.pageName = pageName;
		browser = (String) executeJavascript("return navigator.userAgent;");
		int timeout;
		if (System.getProperty("timeout") != null) {
			timeout = Integer.parseInt(System.getProperty("timeout"));
		} else {
			timeout = Integer.parseInt(getProperty("Config.properties", "timeout"));
		}
		this.wait = new SeleniumWait(driver, timeout);
		browser = (String) executeJavascript("return navigator.userAgent;");
		this.msg = new Msg();
	}
	
	public BaseUi() {
		
	}

	protected Object executeJavascript(String script) {
		return ((JavascriptExecutor) driver).executeScript(script);
	}

	protected Object executeJavascript(String method, WebElement token) {
		return ((JavascriptExecutor) driver).executeScript("arguments[0]." + method, token);
	}

	protected String getPageTitle() {
		return driver.getTitle().trim();
	}

	public void logMessage(String string) {
		msg.log(string);
	}

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}
}
