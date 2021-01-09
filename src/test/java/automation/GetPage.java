package automation;

import static automation.ObjectFileReader.getELementFromFile;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automation.Locators;

public class GetPage extends BaseUi {

	protected WebDriver driver;
	String pageName;

	public GetPage() {
		super();
	}

	public GetPage(WebDriver driver, String pageName) {
		super(driver, pageName);
		this.driver = driver;
		this.pageName = pageName;
	}
	
	/**
	 * ******** Get Element **********
	 */
	protected WebElement element(String elementToken) {
		return _element(elementToken, "", "");
	}

	protected WebElement element(String elementToken, String replacement) {
		return _element(elementToken, replacement, "");
	}

	protected WebElement element(String elementToken, String replacement1, String replacement2) {
		return _element(elementToken, replacement1, replacement2);
	}

	protected WebElement element(String elementToken, String replacement1, String replacement2, String replacement3) {
		return driver.findElement(getLocator(elementToken, replacement1, replacement2, replacement3));
	}

	protected WebElement element(By locator) {
		return driver.findElement(locator);
	}
	
	/**
	 * ******** Get List of Elements **********
	 */
	protected List<WebElement> elements(String elementToken) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		return driver.findElements(getLocators(locator[1].trim(), locator[2].trim()));
	}
	
	private WebElement _element(String elementToken, String replacement1, String replacement2) {
		if (replacement1.isEmpty() && replacement2.isEmpty()) {
			return driver.findElement(getLocator(elementToken));
		} else if (replacement2.isEmpty() && !replacement1.isEmpty()) {
			return driver.findElement(getLocator(elementToken, replacement1));
		} else if (!replacement1.isEmpty() && !replacement2.isEmpty()) {
			return driver.findElement(getLocator(elementToken, replacement1, replacement2));
		}
		return driver.findElement(getLocator(elementToken));
	}

	private WebElement _elementWithWait(String elementToken, String replacement1, String replacement2) {
		//		wait.getWhenVisible(getLocator(elementToken));
		if (replacement1.isEmpty() && replacement2.isEmpty()) {
			return wait.getWhenVisible(getLocator(elementToken));
		} else if (replacement2.isEmpty() && !replacement1.isEmpty()) {
			return wait.getWhenVisible(getLocator(elementToken, replacement1));
		} else if (!replacement1.isEmpty() && !replacement2.isEmpty()) {
			return wait.getWhenVisible(getLocator(elementToken, replacement1, replacement2));
		}
		return wait.getWhenVisible(getLocator(elementToken));
	}
	
	/**
	 * ******** Get XPath from file **********
	 */
	public String getXPathFromFile(String elementToken) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		return locator[2].trim();
	}

	public String getXPathFromFile(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+?\\}", replacement);
		return locator[2].trim();
	}

	public String getXPathFromFile(String elementToken, String replacement1, String replacement2) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement2);
		return locator[2].trim();
	}

	protected By getLocator(String elementToken) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		return getLocators(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String replacement) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceAll("\\$\\{.+?\\}", replacement);
		return getLocators(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String replacement1, String replacement2) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement2);
		return getLocators(locator[1].trim(), locator[2].trim());
	}

	protected By getLocator(String elementToken, String replacement1, String replacement2, String replacement3) {
		String[] locator = getELementFromFile(this.pageName, elementToken);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement1);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement2);
		locator[2] = locator[2].replaceFirst("\\$\\{.+?\\}", replacement3);
		return getLocators(locator[1].trim(), locator[2].trim());
	}

	// TODO rename to distiguish between getlocator and getlocators
	private By getLocators(String locatorType, String locatorValue) {
		switch (Locators.valueOf(locatorType)) {
		case id:
			return By.id(locatorValue);
		case xpath:
			return By.xpath(locatorValue);
		case name:
			return By.name(locatorValue);
		case classname:
			return By.className(locatorValue);
		case css:
			return By.cssSelector(locatorValue);
		case linktext:
			return By.linkText(locatorValue);
		default:
			return By.id(locatorValue);
		}
	}
}