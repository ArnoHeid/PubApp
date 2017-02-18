package de.hsmainz.pubapp.routing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Handles Properties Files
 *
 * @author Sarah
 * @since 18.02.2017.
 */
@SuppressWarnings("serial")
public class MyProperties extends Properties {

	// ****************************************
	// CONSTANTS
	// ****************************************

	private static final Logger logger = LogManager.getLogger(MyProperties.class);

	// ****************************************
	// VARIABLES
	// ****************************************

	private static MyProperties instance = null;
	private static File propertiesFile;
	private static String defaultPropertiesFile = "/default.properties";

	// ****************************************
	// INIT/CONSTRUCTOR
	// ****************************************

	private MyProperties() {

	}

	// ****************************************
	// GETTER/SETTER
	// ****************************************

	static void setPropertiesFile(String propertiesFileName) {
		File newProp = new File(propertiesFileName);
		if (newProp.exists())
			propertiesFile = newProp;
	}

	// ****************************************
	// PUBLIC METHODS
	// ****************************************

	/**
	 * This method returns and loads if necessary the properties.
	 *
	 * @return the Instance of the MyProperties class
	 */
	public static MyProperties getInstance() {
		if (instance == null) {
			try {
				instance = new MyProperties();
				InputStream in = MyProperties.class.getClass().getResourceAsStream(defaultPropertiesFile);
				instance.load(in);
				in.close();

				if (propertiesFile != null) {
					FileInputStream fin = new FileInputStream(propertiesFile);
					instance.load(fin);
					fin.close();
				}

			} catch (IOException e) {
				logger.error(e);
				return null;
			}
		}

		return instance;
	}

	// ****************************************
	// PRIVATE METHODS
	// ****************************************

	// ****************************************
	// INNER CLASSES
	// ****************************************

}
