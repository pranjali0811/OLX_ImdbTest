package test.actions;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import automation.GetPage;

public class SearchPage extends GetPage {

	public SearchPage(WebDriver driver) {
		super(driver, "SearchPage");
	}

	public void clickOnTheGivenSectionInCategorySearchBox(String sectionName) {
		element("categorySearchOptions",sectionName).click();
		logMessage("User clicked on the section: "+sectionName+" under the Category Search box.");
	}
	
	public ArrayList<String> getSearchResults() {
		List<WebElement> resultSet = elements("searchResult");
		ArrayList<String> results = new ArrayList<>();
		for(WebElement e: resultSet) {
			results.add(e.getText());
		}
		return results;		
	}
	
	public Boolean verifySearchResultWithAPI(ArrayList<String> searchResultAPI){
		int c=0;
		ArrayList<String> uiResults= getSearchResults();
		for(int i=0;i<uiResults.size();i++){
//			System.out.println("UI======="+uiResults.get(i));
//			System.out.println("API======="+searchResultAPI.get(c));
			if(uiResults.get(i).equalsIgnoreCase(searchResultAPI.get(c))){
				c++;
			}	
		}
//		System.out.println("------C=="+c);
		if(c==searchResultAPI.size())
			return true;
		else
			return false;
	}
}
