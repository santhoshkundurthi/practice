package com.anz.file.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.anz.file.reader.CreditLimitFileReader;

public class PropertiesFileReader {
	private static final Logger LOGGER = Logger.getLogger(CreditLimitFileReader.class.getName());

	public static Properties readPropertiesFile(String fileName) throws IOException {
		LOGGER.info("-----------PropertiesFileReader.readPropertiesFile() file location reading started -----------");

		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(fileName);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException e) {
			LOGGER.info("FileNotFoundException " + e);

		} catch (IOException ioe) {
			LOGGER.info("FileNotFoundException " + ioe);
		} finally {
			fis.close();
		}
		LOGGER.info("-----------PropertiesFileReader.readPropertiesFile() file location completed -----------");
		LOGGER.info("File location Path :-------------"+prop);
		return prop;
	}
}
