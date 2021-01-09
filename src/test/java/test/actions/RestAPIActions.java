package test.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;


import automation.GetPage;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.jayway.jsonpath.JsonPath;

public class RestAPIActions extends GetPage {

	public RestAPIActions(WebDriver driver) {
		super(driver, "RestAPIActions");
	}

	public Response getResponseOfServiceWithDesiredParameters(String apiBaseURL, Map<String, String> parameters) {
		RestAssured.baseURI = apiBaseURL;
		RequestSpecification request = RestAssured.given();
		Response response = request.queryParams(parameters).get();
		return response;
	}

	public Map<String, String> getQueryParameters(Map<String, Object> parameters) {
		Map<String, String> queryParams = new HashMap<String, String>();
		for (Map.Entry<String, Object> entry : parameters.entrySet()) {
			queryParams.put(entry.getKey(), entry.getValue().toString());
		}
		return queryParams;
	}

	public ArrayList<String> getTitlesArrayFromResponse(Response res,ArrayList<String> expectedtitles) throws ParseException {
		ArrayList<String> titles = new ArrayList<>();
		JSONParser parse = new JSONParser();
		JSONObject data_obj = (JSONObject) parse.parse(res.getBody().asString());
		JSONArray obj = (JSONArray) (data_obj).get("Search");
		for (int i = 0; i < obj.size(); i++) {
			 String title = JsonPath.read(data_obj, "$.Search[" + i + "].Title");
			 if(title.equals(expectedtitles.get(0))||
					 title.equals(expectedtitles.get(1))||
					 title.equals(expectedtitles.get(2)))
			 titles.add(title);
		}
//		System.out.println("Titles from API Search"+titles.asString());
		return titles;
	}

}
