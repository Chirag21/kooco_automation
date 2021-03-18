package utils;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigHelper {
	private static Properties prop;
	private static FileInputStream fis;

	private ConfigHelper() {
	}

	public static String getProperty(String key) {
		prop = new Properties();
		try {
			fis = new FileInputStream(".//src//main//resources//config.properties");
			prop.load(fis);
		} catch (IOException e) {
			System.err.println("Cannot read config.properties");
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			System.err.println("Cannot close input stream");
			e.printStackTrace();
		}

		return prop.getProperty(key).trim();

	}

	// returns width and height of screen resolution
	public static int[] getScreenSize() {
		return new int[] { Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height };
	}
}
