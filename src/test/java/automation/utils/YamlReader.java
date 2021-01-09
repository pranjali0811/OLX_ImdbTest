/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package automation.utils;

import static automation.utils.DataReadWrite.getProperty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;
import org.yaml.snakeyaml.Yaml;

@SuppressWarnings("unchecked")
public class YamlReader {

	private static String yamlFilePath;
	private static String tier = System.getProperty("tier");
	private static String tier1= null;

	@SuppressWarnings("resource")
	public static String setYamlFilePath() {
		if (tier == null || tier.length() == 0) {
			tier = getProperty("Config.properties", "tier").trim();
		}
		if (tier.equalsIgnoreCase("test")) {
				yamlFilePath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "testdata"
						+ File.separator + "TEST_TestData.yml";
		}
		System.out.println("Yaml file path :: " + yamlFilePath);
		try {
			new FileReader(yamlFilePath);
		} catch (FileNotFoundException e) {
			Reporter.log("Wrong Tier!!!", true);
			System.exit(0);
		}
		return yamlFilePath;
	}

	public static void setTier(String env) {
		tier1 = env;
	}

	public static String getTier() {
		return tier;
	}

	public static String getYamlValue(String token) {
		if (yamlFilePath == null) {
			setYamlFilePath();
		}
		return getValue(token);
	}

	public static String getYamlValue(String yamlPath, String token) {
		if (yamlFilePath == null) {
			setYamlFilePath();
		}
		return getValue(yamlPath, token);
	}

	@Deprecated
	public static String getData(String token) {
		return getYamlValue(token);
	}

	public static Map<String, Object> getYamlValues(String token) {
		if (yamlFilePath == null) {
			setYamlFilePath();
		}
		Reader doc;
		try {
			doc = new FileReader(yamlFilePath);
		} catch (FileNotFoundException ex) {
			System.out.println("File not valid or missing!!!");
			ex.printStackTrace();
			return null;
		}
		Yaml yaml = new Yaml();
		// TODO: check the type casting of object into the Map and create
		// instance in one place
		Map<String, Object> object = (Map<String, Object>) yaml.load(doc);
		return parseMap(object, token + ".");
	}

	private static String getValue(String token) {
		Reader doc = null;
		try {
			doc = new FileReader(yamlFilePath);
		} catch (FileNotFoundException e) {
			Reporter.log("Wrong tier", true);
			return null;
		}
		Yaml yaml = new Yaml();
		Map<String, Object> object = (Map<String, Object>) yaml.load(doc);
		try {
			return getMapValue(object, token);
		} catch (Exception e) {
			return null;
		}

	}

	private static String getValue(String yamlPath, String token) {
		Reader doc = null;
		try {
			doc = new FileReader(yamlPath);
		} catch (FileNotFoundException e) {
			Reporter.log("Wrong tier", true);
			return null;
		}
		Yaml yaml = new Yaml();
		Map<String, Object> object = (Map<String, Object>) yaml.load(doc);
		try {
			return getMapValue(object, token);
		} catch (Exception e) {
			return null;
		}

	}

	private static String getMapValue(Map<String, Object> object, String token) {
		// TODO: check for proper yaml token string based on presence of '.'
		String[] st = token.split("\\.");
		return parseMap(object, token).get(st[st.length - 1]).toString();
	}

	private static Map<String, Object> parseMap(Map<String, Object> object, String token) {
		if (token.contains(".")) {
			String[] st = token.split("\\.");
			object = parseMap((Map<String, Object>) object.get(st[0]), token.replace(st[0] + ".", ""));
		}
		return object;
	}

	public static int generateRandomNumber(int MinRange, int MaxRange) {
		int randomNumber = MinRange + (int) (Math.random() * ((MaxRange - MinRange) + 1));
		return randomNumber;
	}
	/**
	 * The List returned by this method is fixed size list.
	 * Value splits happens on the basis of character - '|'
	 * @param token
	 * @return
	 */
	public static List<String> getYamlValuesAsList(String token) {
		List<String> valList = Arrays.asList(getYamlValue(token).split("\\|"));

		Msg.logMessage("Yaml List Fetched :" + valList);
		return valList;
	}

	public static List<String> getYamlValuesAsSortedList(String token) {
		List<String> valList = Arrays.asList(getYamlValue(token).split("\\|"));
		valList.sort(Comparator.naturalOrder());
		Msg.logMessage("Yaml List Fetched :" + valList);
		return valList;
	}
	
	
	/**
	 * 
	 * Custom Additions
	 * 
	 * 
	 */
	public static String getYamlValueWithReplacement(String token, String replacement) {
		String value = getYamlValue(token);
		value = value.replaceAll("\\$\\{.+?\\}", replacement);
		return value;
	}
	
	public static String getYamlValueWithReplacement(String token, String replacement1, String replacement2) {
		String value = getYamlValue(token);
		value = value.replaceFirst("\\$\\{.+?\\}", replacement1);
		value = value.replaceFirst("\\$\\{.+?\\}", replacement2);
		return value;
	}
	
	public static String getYamlValueWithReplacement(String token, String replacement1, String replacement2,String replacement3,String replacement4,String replacement5) {
		String value = getYamlValue(token);
		value = value.replaceFirst("\\$\\{.+?\\}", replacement1);
		value = value.replaceFirst("\\$\\{.+?\\}", replacement2);
		value = value.replaceFirst("\\$\\{.+?\\}", replacement3);
		value = value.replaceFirst("\\$\\{.+?\\}", replacement4);
		value = value.replaceFirst("\\$\\{.+?\\}", replacement5);
		return value;
	}
	
	/**
	 * The List returned by this method is fixed size list.
	 * Value splits happens on the basis of space and new line - ' '
	 * Return value would have underscore replaced by space.
	 * @param token
	 * @return
	 */
	public static ArrayList<String> getYamlList(String token) {
		String[] dataList = (getYamlValue(token)).split(" ");
		ArrayList<String> list = new ArrayList<String>();
		for (String element : dataList) {
			list.add(element.replaceAll("_", " "));
		}
		list.sort(Comparator.naturalOrder());
		return list;
	}
	
	public static ArrayList<String> getYamlListOfMapValues(String token) {
		String[] dataList = (getYamlValue(token)).split(" ");
		ArrayList<String> list = new ArrayList<String>();
		for (String element : dataList) {
			list.add(element.replaceAll("_", " "));
		}
		return list;
	}
}
