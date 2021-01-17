import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class Main {

	private static final String PROP_FILE_NAME = "config.properties";
	private static final String SRC_KEY = "src";
	private static final String DEST_KEY = "dest";

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {

		// Log JAVA_HOME.
		writeLog("JAVA_HOME=" + System.getProperty("java.home"));

		// Load properties.
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(PROP_FILE_NAME));
		} catch (Exception e) {
			writeLog("Creating " + PROP_FILE_NAME + ".");
			prop.setProperty(SRC_KEY, "");
			prop.setProperty(DEST_KEY, "");
			prop.store(new FileOutputStream(PROP_FILE_NAME), null);
		}

	}

	private static void writeLog(String message) {

		System.out.println("[" + new Date() + "] " + message);

	}

}
