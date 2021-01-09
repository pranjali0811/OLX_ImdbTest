/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automation;

import static automation.utils.DataReadWrite.getProperty;
import static automation.utils.YamlReader.getYamlValue;
import static automation.utils.YamlReader.setYamlFilePath;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;

import automation.utils.Msg;
import automation.utils.SeleniumWait;
import test.actions.HomePage;
import test.actions.RestAPIActions;
import test.actions.SearchPage;

public class TestSessionInitiator {

	public WebDriver driver;
	public static WebDriver originalDriver;
	private WebDriverFactory wdfactory;
	Map<String, Object> chromeOptions = null;
	protected static String product;
	SeleniumWait wait;
	public HomePage homepage;
	public SearchPage searchPage;
	public RestAPIActions restAPI;
	
	public TestSessionInitiator() {
		wdfactory = new WebDriverFactory();
		setYamlFilePath();
		configureBrowser();
		_initPage();
	}
	
	public void _initPage() {
		homepage = new HomePage(driver);
		searchPage = new SearchPage(driver);
		restAPI =  new RestAPIActions(driver);
	}

	protected void configureBrowser() {
		driver = wdfactory.getDriver(_getSessionConfig());
		System.out.println("driver is: " + driver);
		String browser = getBrowser();
		if (!browser.equalsIgnoreCase("mobile") && !browser.equalsIgnoreCase("safariIphone")) {
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
		}
		int timeout;
		if (System.getProperty("timeout") != null) {
			timeout = Integer.parseInt(System.getProperty("timeout"));
		} else {
			timeout = Integer.parseInt(_getSessionConfig().get("timeout"));
		}
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		originalDriver = driver;
		wait = new SeleniumWait(driver, Integer.parseInt(getProperty("Config.properties", "timeout")));
	}

	protected void configureBrowser(boolean proxyFlag) {
		Map<String, String> proxySetter = _getSessionConfig();
		if (proxyFlag == true) {
			proxySetter.put("browserProxy", "Proxy");
		}

		driver = wdfactory.getDriver(proxySetter);
		String browser = getBrowser();
		if (!browser.equalsIgnoreCase("mobile") && !browser.equalsIgnoreCase("safariIphone")) {
			driver.manage().window().maximize();
		}
		int timeout;
		if (System.getProperty("timeout") != null) {
			timeout = Integer.parseInt(System.getProperty("timeout"));
		} else {
			timeout = Integer.parseInt(_getSessionConfig().get("timeout"));
		}

		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		originalDriver = driver;
		wait = new SeleniumWait(driver, Integer.parseInt(getProperty("Config.properties", "timeout")));
	}

	private static Map<String, String> _getSessionConfig() {
		String[] configKeys = { "tier", "browser",
				"timeout", "destroyBrowserSession"};
		Map<String, String> config = new HashMap<String, String>();
		for (String string : configKeys) {
			try {
				if (System.getProperty(string).isEmpty())
					config.put(string, getProperty("./Config.properties", string));
				else
					config.put(string, System.getProperty(string));

			} catch (NullPointerException e) {
				config.put(string, getProperty("./Config.properties", string));

			}
		}
		return config;
	}

	public static String getEnv() {
		String tier = System.getProperty("tier");
		if (tier == null) {
			tier = _getSessionConfig().get("tier");
		}
		return tier;
	}

	public static String getBrowser() {
		String browser = System.getProperty("browser");
		if (browser == null) {
			browser = _getSessionConfig().get("browser");
		}
		return browser;
	}

	public static String getDestroyBrowserSession() {
		try {
			return _getSessionConfig().get("destroyBrowserSession").toUpperCase();
		} catch (NullPointerException e) {
			return "";
		}
	}

	public void launchApplication() {
		launchApplication(getYamlValue("app_url"));
	}

	public void launchApplication(String applicationpath) {
		driver.manage().deleteAllCookies();
		driver.manage().deleteAllCookies();
		System.out.println("Cookies deleted");
		Reporter.log("Application url is :- " + applicationpath, true);
		Reporter.log("Test browser is :- " + getBrowser(), true);
		driver.get(applicationpath);
		wait.waitForPageToLoadCompletely();
	}

	public void getURL(String url) {
		driver.manage().deleteAllCookies();
		System.out.println("Url is--->>" + url);
		driver.get(url);
	}

	public void setWindowSize(int width, int height) {
		driver.manage().window().setSize(new Dimension(width, height));
	}

	public void closeBrowserSession() {
		if (!(getDestroyBrowserSession().equals("NO") || getDestroyBrowserSession().equals("FALSE")))
			driver.quit();
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public void navigateTo(String URL) {
		driver.navigate().to(URL);
	}

	public void stepStartMessage(String testStepName) {
		Reporter.log(" ", true);
		Reporter.log("***** STARTING TEST STEP:- " + testStepName.toUpperCase() + " *****", true);
		Reporter.log(" ", true);
	}

	public void refreshPage() {
		driver.navigate().refresh();
	}

	public void closeWindow() {
		driver.close();
	}

	public void nextTab() {
		new Actions(driver).sendKeys(Keys.chord(Keys.CONTROL, Keys.TAB)).perform();
	}

	public void verifyCurrentUrl(String url) {
		String curentUrl = driver.getCurrentUrl();
		Assert.assertTrue(curentUrl.contains(url), "ASSERT FAILED: current url not matched");
		Msg.reportPass("Current url is " + curentUrl);
	}

}
