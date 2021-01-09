package test.testsclasses;

import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import static automation.utils.YamlReader.getYamlValue;
import static automation.utils.YamlReader.getYamlValues;

import java.util.ArrayList;
import java.util.Map;

import automation.utils.Parent_Test;
import io.restassured.response.Response;
import test.actions.RestAPIActions;

public class ImdbTest extends Parent_Test {
	
	public String apiKey, apiBaseURL, expectedTitle1, expectedTitle2, expectedTitle3;
	public String searchInput;
	public Response res;
	public Map<String, Object> queryParams;
	public RestAPIActions restAPI;
	public ArrayList<String> expectedTitles = new ArrayList<>();
	public ArrayList<String> expectedTitlesFromAPI = new ArrayList<>();
	
	public ImdbTest() {
		apiBaseURL =  getYamlValue("apiBaseURL");
		queryParams =  getYamlValues("QueryParameters");
		expectedTitle1 =  getYamlValue("ExpectedTitle1");
		expectedTitle2 =  getYamlValue("ExpectedTitle2");
		expectedTitle3 =  getYamlValue("ExpectedTitle3");
		searchInput = getYamlValue("QueryParameters.s");
	}
	
	@Test()
	private void getResponseFromImdbService() throws ParseException {
		res= test.restAPI.getResponseOfServiceWithDesiredParameters
				(apiBaseURL, test.restAPI.getQueryParameters(queryParams));
		Assert.assertEquals(res.getStatusCode(), 200);
		expectedTitles.add(expectedTitle1);
		expectedTitles.add(expectedTitle2);
		expectedTitles.add(expectedTitle3);
		expectedTitlesFromAPI = test.restAPI.getTitlesArrayFromResponse(res, expectedTitles);
	}

	@Test(dependsOnMethods = "getResponseFromImdbService")
	private void launchApp() {
		test.launchApplication();
	}
	
	@Test(dependsOnMethods = "launchApp")
	private void searchTheDesiredInput() {
		test.homepage.searchForDesiredInput(searchInput);
	}
	
	@Test(dependsOnMethods = "searchTheDesiredInput")
	private void getTheSearchedResults() {
		test.searchPage.clickOnTheGivenSectionInCategorySearchBox("Movie");
	}
	
	@Test(dependsOnMethods = "getTheSearchedResults")
	private void validateTheSearchedResults() {
		Assert.assertTrue(test.searchPage.verifySearchResultWithAPI(expectedTitlesFromAPI), "Displaying results table doesn't contains movie titles from filtered response ");
	}
	
}
