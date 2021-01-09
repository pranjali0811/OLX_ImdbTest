package automation;

import static automation.utils.DataReadWrite.getProperty;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.testng.Assert;

import automation.TestSessionInitiator;
import automation.utils.DataReadWrite;

/**
 * This class reads the PageObjectRepository text files. Uses buffer reader.
 *
 * @author pranjaliJaiswal
 *
 */
public class ObjectFileReader {

	static String tier;
	static String filepath = "src/test/resources/PageObjectRepository/";

	public static String[] getELementFromFile(String pageName, String elementName) {
		setTier();
		BufferedReader br = null;
		String matchingLine = "";
		try {
			String filePathString = filepath + File.separator + tier + File.separator + pageName + ".txt";
			File f = new File(filePathString);
			if (!f.exists()) {
				System.out.println("WARNING : '" + tier + "/" + pageName +".txt' file not found");
			} else {
				br = new BufferedReader(new FileReader(filePathString));
				String line = br.readLine();
				while (line != null) {
					if (line.split(":", 3)[0].equalsIgnoreCase(elementName)) {
						matchingLine = line;
						break;
					}
					line = br.readLine();
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return matchingLine.split(":", 3);

	}

	public static String getPageTitleFromFile(String pageName) {
		setTier();
		BufferedReader br = null;
		String returnElement = "";
		try {
			br = new BufferedReader(
					new FileReader(filepath + File.separator + tier + File.separator + pageName + ".txt"));
			String line = br.readLine();

			while (line != null) {
				if (line.split(":", 3)[0].equalsIgnoreCase("pagetitle")
						|| line.split(":", 3)[0].equalsIgnoreCase("title")) {
					returnElement = line;
					break;
				}
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return returnElement.split(":", 3)[1].trim();
	}

	private static void setTier() {
		if (System.getProperty("tier") != null)
			tier = System.getProperty("tier").toUpperCase();
		else {
			tier = Tiers.valueOf(getProperty("Config.properties", "tier")).toString();
		}
		switch (Tiers.valueOf(tier)) {
		case Test:
		case TEST:
		case test:
			tier = "TEST";
			break;
		default:
			break;
		}
	}
}
