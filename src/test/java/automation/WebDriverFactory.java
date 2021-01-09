package automation;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import automation.Browsers;

public class WebDriverFactory {
	static String currentDir = System.getProperty("user.dir");
	static String downloadPath = currentDir + File.separator + "Downloads" + File.separator;

	private static String browser;
	private static String chromeDriverPath = "src/test/resources/drivers/chromedriver";
	private static String chromeDriverPathLinux = "src/test/resources/drivers/chromedriver_linux";
	private static String extensionPath = currentDir + "/src/test/resources/extensions/proxy_for_chrome";

	public WebDriver getDriver(Map<String, String> seleniumconfig) {
		browser = System.getProperty("browser");
		if (browser == null || browser.length() == 0) {
			browser = seleniumconfig.get("browser").toString();
		}
	    else {
			switch (Browsers.valueOf(browser)) {
			case chrome:
			case CHROME:
				System.out.println("OS is: " + System.getProperty("os.name"));
				if (System.getProperty("os.name").equals("Mac OS X")) {
					System.out.println("With out extension");
					return getChromeDriver(chromeDriverPath);
				} else {
					if (System.getProperty("os.name").equals("Linux")) {
						System.out.println("With out extension");
						return getChromeDriver(chromeDriverPathLinux);
					} else {
						if (seleniumconfig.get("browserProxy") != null) {
							System.out.println("extension destination is: " + extensionPath);
							return getChromeDriver(chromeDriverPath + ".exe", extensionPath);
						} else
							return getChromeDriver(chromeDriverPath + ".exe");
					}
			}
			default:
				return getChromeDriver(chromeDriverPath + ".exe");
			} }
		return getChromeDriver(chromeDriverPath + ".exe");
	}

	private static ChromeOptions getChromeOption() {
		ChromeOptions options = new ChromeOptions();
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.BROWSER, Level.ALL);
		options.addArguments("--always-authorize-plugins=true");
		options.addArguments("start-maximized");
		options.addArguments("--disable--extensions");
		options.addArguments("disable--extensions");
		options.addArguments("--disable-popup-blocking");
		return options;
	}
	
	private static WebDriver getChromeDriver(String driverpath) {
		System.setProperty("webdriver.chrome.driver", driverpath);
		ChromeOptions options = getChromeOption();
		options.addArguments("--always-authorize-plugins=true");
		options.addArguments("start-maximized");
		options.addArguments("--disable-notifications");
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("download.default_directory", downloadPath);
		options.setExperimentalOption("prefs", chromePrefs);
		return new ChromeDriver(options);
	}

	private static WebDriver getChromeDriver(String driverpath, String extensionPath) {
		System.setProperty("webdriver.chrome.driver", driverpath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--always-authorize-plugins=true");
		options.addArguments("start-maximized");
		options.addArguments("--disable-notifications");
		options.addArguments("-load-extension=" + extensionPath);
		return new ChromeDriver(options);
	}

}