package utils;

import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class PropertyUtils {
	private static Properties prop;
	private static FileInputStream fis;

	private PropertyUtils() {
	}

	public static String getProperty(String key) {
		prop = new Properties();
		try {
			fis = new FileInputStream(".//src//main//resources//config.properties");
			if (Objects.nonNull(fis) || Objects.nonNull(prop))
				prop.load(fis);
		} catch (IOException e) {
			System.err.println("Cannot read config.properties");
			e.printStackTrace();
		} finally {
			try {
				Objects.requireNonNull(fis, "fis is null!!").close();
			} catch (IOException e) {
				System.err.println("Cannot close input stream");
				e.printStackTrace();
			}
		}
		return prop.getProperty(Objects.requireNonNull(key, "key is null!!!")).trim();

	}

	// returns width and height of screen resolution
	public static int[] getScreenSize() {
		return new int[] { Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height };
	}
}
