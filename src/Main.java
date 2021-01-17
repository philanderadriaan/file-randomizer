import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main
{

	/**
	 * Properties file name.
	 */
	private static final String PROP_FILE_NAME = "config.properties";

	/**
	 * Source folder key in properties file.
	 */
	private static final String SRC_KEY = "src";

	/**
	 * Destination folder key in properties file.
	 */
	private static final String DEST_KEY = "dest";

	/**
	 * Properties object.
	 */
	private static final Properties PROP = new Properties();

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{
		// Log JAVA_HOME.
		log("JAVA_HOME=" + System.getProperty("java.home"));

		try
		{
			// Load properties from file.
			PROP.load(new FileInputStream(PROP_FILE_NAME));
		}
		catch (Exception e)
		{
			// Set UI look and feel to current system.
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			// Choose directories for source and destination.
			chooseDir(SRC_KEY, "source");
			chooseDir(DEST_KEY, "destination");

			// Creates new properties file.
			log("Creating " + PROP_FILE_NAME + ".");
			PROP.store(new FileOutputStream(PROP_FILE_NAME), null);
		}

	}

	/**
	 * Choose directory to add to properties file.
	 * 
	 * @param key
	 */
	private static void chooseDir(String key, String title)
	{

		// Set file chooser to directories only.
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Select " + title + " folder");

		// Choose directory.
		int option = chooser.showOpenDialog(null);
		if (option == JFileChooser.APPROVE_OPTION)
		{

			// Save directory to properties object.
			PROP.setProperty(key, chooser.getSelectedFile().getAbsolutePath());
		}
	}

	/**
	 * Writes to log.
	 * 
	 * @param msg
	 */
	private static void log(String msg)
	{

		// Adds timestamp before log message.
		System.out.println(new Date() + "\t" + msg);

	}

}
