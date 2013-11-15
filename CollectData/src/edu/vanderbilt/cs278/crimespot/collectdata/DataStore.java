package edu.vanderbilt.cs278.crimespot.collectdata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Help to save data into files
 * 
 * @author Li
 * 
 */
public class DataStore {
	private static String OUTPUT = "data/";
	private final static String CLASSNAME = "report-grid";
	private final static String TITLE = "title";

	/**
	 * a file will be created if not created, or expand to the existing file
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public static PrintWriter getPWByName(String name) throws IOException {
		String temp = OUTPUT + name;
		File file = new File(temp);
		if (!file.exists()) {
			file.createNewFile();
		}
		return new PrintWriter(new BufferedWriter(new FileWriter(temp, true)));

	}

	/**
	 * get history data
	 * 
	 * @param name
	 */
	public static void getHistoryDataByName(String name) {
		List<String> list = Url.getUrlListbyName(name);
		try {
			PrintWriter pw = DataStore.getPWByName(name);
			for (String url : list) {
				System.out.println(url);
				getDataByUrl(url, pw, name);
			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * get current data
	 * 
	 * @param name
	 */
	public static void getDataByName(String name) {
		String url = Url.getUrlbyName(name);
		System.out.println(url);
		try {
			PrintWriter pw = DataStore.getPWByName(name);
			getDataByUrl(url, pw, name);
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * open the url and get the crime data and print into the printwriter
	 * 
	 * @param url
	 * @param pw
	 */
	public static void getDataByUrl(String url, PrintWriter pw, String name) {
		WebDriver driver = new HtmlUnitDriver();
		driver.get(url);
		// check if there is any update
		WebElement title = driver.findElement(By.className(TITLE));
		if (title.getText().startsWith("No")) {
			System.out.println("no new crime data updated now here");
			return;
		}
		// Find the text input element by its name
		WebElement table = driver.findElement(By.className(CLASSNAME));
		List<WebElement> allRows = table.findElements(By.tagName("tr"));

		// And iterate over them, getting the cells
		// make them sorted from the oldest to the newest
		for (int i = allRows.size() - 1; i >= 0; i--) {
			List<WebElement> cells = allRows.get(i).findElements(
					By.tagName("td"));
			if (cells.size() > 5) {
				pw.println(cells.get(1).getText()
						+ "\t"
						+ cells.get(5).getText()
						+ "\t"
						+ (cells.get(3).getText().replaceAll(" ", "_")
								.toString()
								+ "_" + name));
			} else
				break;
		}
	}
}
