package test.actions;

import org.openqa.selenium.WebDriver;

import automation.GetPage;

public class HomePage extends GetPage {

	public HomePage(WebDriver driver) {
		super(driver, "HomePage");
	}
	
	public void searchForDesiredInput(String value) {
		element("searchBar").sendKeys(value);
		logMessage("User entered the value in search bar:: "+value);
		element("searchIcon").click();
		logMessage("User clicked on the search icon.");
	}

}
