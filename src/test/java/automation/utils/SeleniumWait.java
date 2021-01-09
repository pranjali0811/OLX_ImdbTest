package automation.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Beta;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWait {

	WebDriver driver;
	WebDriverWait wait;
	FluentWait<WebDriver> fluentWait;
	static int count = 0;

	public int timeout;
	public int configTimeOut;

	public SeleniumWait(WebDriver driver, int timeout) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, timeout);
		this.timeout = timeout;
		this.configTimeOut = timeout;
	}

	/**
	 * Returns webElement found by the locator if element is visible
	 *
	 * @param locator
	 * @return
	 */
	public WebElement getWhenVisible(By locator) {
		WebElement element;
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		return element;
	}

	public WebElement getWhenClickable(By locator) {
		WebElement element;
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		return element;
	}

	public boolean waitForPageTitleToBeExact(String expectedPagetitle) {
		return wait.until(ExpectedConditions.titleIs(expectedPagetitle));
	}

	public boolean waitForPageTitleToContain(String expectedPagetitle) {
		return wait.until(ExpectedConditions.titleContains(expectedPagetitle));
	}

	public WebElement waitForElementToBeVisible(WebElement element) {
		try {
			return wait.until(ExpectedConditions.visibilityOf(element));
		} catch (StaleElementReferenceException e) {
			return wait.until(ExpectedConditions.visibilityOf(element));
		}
	}

	public WebElement waitForElementToBeVisible(By locator) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (StaleElementReferenceException e) {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		}
	}

	public void waitForFrameToBeAvailableAndSwitchToIt(By locator) {
		try {
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
			Msg.logAction("Switched to frame with locator :: " + locator.toString());
		} catch (TimeoutException te) {
			Msg.logFailure("Waited Until timeout but no frame found with locator :: " + locator.toString());
			throw new NoSuchElementException("No Frame was found to switch into.");
		} catch (WebDriverException we) {
			hardWait(2);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
		}

	}

	public List<WebElement> waitForElementsToBeVisible(List<WebElement> elements) {
		return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	public List<WebElement> waitForPresenceOfAllElements(By locator) {
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}
	
	public WebElement waitForPresenceOfElementLocated(By locator) {
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public boolean waitForElementToBeInVisible(By locator) {
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	public WebElement waitForElementToBeClickable(WebElement element) {
		return wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public WebElement waitForElementToBeClickable(By locator) {
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	public List<WebElement> waitForNumberOfElementsToBeMoreThanZero(By locator) {
		hardWait(2);
		List<WebElement> elements = fluentWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		int attempts = 0, elementLocated = elements.size();
		while (elementLocated <= 0 && attempts < timeout * 1000) {
			hardWaitMillis(500);
			elements = fluentWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
			elementLocated = elements.size();
			attempts = attempts + 500;
		}
		if (elementLocated > 0)
			return elements;
		else
			throw new NoSuchElementException("No Elements with locator : " + locator + " was located.");
	}

	public boolean waitUntilNewWindowIsOpen(int numOfWindows) {
		return wait.until(ExpectedConditions.numberOfWindowsToBe(numOfWindows));
	}

	public void waitForElementToDisappear(WebElement element) {
		int i = 0;
		resetImplicitTimeout(3);
		try {
			while (element.isDisplayed() && i <= timeout) {
				hardWait(1);
				i++;
			}
		} catch (Exception e) {
		}
		resetImplicitTimeout(timeout);
	}

	public void resetImplicitTimeout(int newTimeOut) {
		try {
			driver.manage().timeouts().implicitlyWait(newTimeOut, TimeUnit.SECONDS);
		} catch (Exception e) {
		}
	}

	public void setTimeout(int newTimeout) {
		timeout = newTimeout;
	}

	public void waitForPageToLoadCompletely() {
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver wdriver) {
				return String.valueOf(((JavascriptExecutor) wdriver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("*")));
		hardWait(1);
		Msg.logMessage("Page loaded completely");
	}

	public void hardWait(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public void hardWaitMillis(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public int getTimeout() {
		return configTimeOut;
	}

	public void waitForAlertToBePresent() {
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public List<WebElement> getElementsWhenVisible(By locator) {
		List<WebElement> elements;
		elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		return elements;
	}

	/**
	 * This is to be used in case of Stale Element which occurs when
	 * StaleElementException is thrown when the element you were interacting is
	 * destroyed and then in the process of recreation. This methods waits for
	 * timeout specified in config polling every half a second.
	 */
	@Beta
	public List<WebElement> waitForStaleElementsToBeRenewed(List<WebElement> elements) {
		
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(180, TimeUnit.SECONDS)
				.pollingEvery(5,TimeUnit.SECONDS).ignoring(NoSuchElementException.class);                     
		try {
		elements = wait.until(ExpectedConditions.visibilityOfAllElements(elements));
		}
		catch(Exception e)
		{
		}
		return elements;
	}
	
	@Beta
	public WebElement waitForStaleElementToBeRenewed(WebElement element) {
		return waitForStaleElementsToBeRenewed(new ArrayList<WebElement>(Arrays.asList(element))).get(0);
	}

	@Beta
	public List<WebElement> waitForStaleElementsToBeRenewed(By locator) {
		List<WebElement> elements;
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(180, TimeUnit.SECONDS)
				.pollingEvery(5,TimeUnit.SECONDS).ignoring(NoSuchElementException.class);                     
		try {
		elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		}
		catch(Exception e)
		{
			elements = driver.findElements(locator);
		}
		return elements;
	}

	@Beta
	public WebElement waitForStaleElementToBeRenewed(By locator) {
		return waitForStaleElementsToBeRenewed(locator).get(0);
	}

	public String waitForElementToContainSomeText(WebElement elem) {
		String elemTxt;
		double counter = 0;
		JavascriptExecutor jse = (JavascriptExecutor) this.driver;
		waitForStaleElementToBeRenewed(elem);
		while (counter < timeout * 1000) {
			elemTxt = elem.getText().trim().equals("")
					? jse.executeScript("return arguments[0].innerText;", elem).toString()
					: elem.getText();
			elemTxt = elemTxt.trim();
			if (elemTxt.matches("^(?!\\s*$).+")) {
				Msg.logMessage("Found Message : " + elemTxt);
				return elemTxt;
			}
			counter += 250;
			Msg.logMessage("Waited " + counter + " millis for text to appear in the element.");
		}
		Msg.logFailure("Element " + elem.toString() + " had no text inside; waited until timeout");
		return "Waited_untill_timeout_but_found_no_text.";
	}

	protected void scrollToElement(WebElement element) {

		String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";

		((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
	}
	
	public void scrollBy(String x, String y)
	{
		String script = "window.scrollBy(" +x+ "," +y+ ")";
		((JavascriptExecutor) driver).executeScript(script);
		Msg.logMessage("Scrolled by " +x+","+y);
		hardWaitMillis(500);
	}
}
