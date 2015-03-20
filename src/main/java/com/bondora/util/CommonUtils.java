package com.bondora.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class CommonUtils {

	/**
	 * Fetches the value corresponding to the input param from the config file
	 * @param input
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readFromConfig(String input)
			throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(TestConstants.CONFIG_PROPERTY_FILE_PATH));
		String configProperty = properties.getProperty(input);
		return configProperty;
	}
	
}
