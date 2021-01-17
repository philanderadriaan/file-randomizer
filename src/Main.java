import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

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
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException
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

			// Creates new properties file.
			log(e.getMessage());

			// Set UI look and feel to current system.
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			// Choose directories for source and destination.
			chooseDir(SRC_KEY, "source");
			chooseDir(DEST_KEY, "destination");

		}

		// Reset destination directory.
		File destDir = new File((String) PROP.get(DEST_KEY));

		for (File destObj : destDir.listFiles())
		{

			log("Deleting " + destObj.getAbsolutePath());
			destObj.delete();

		}

		// Source directory.
		File srcDir = new File((String) PROP.get(SRC_KEY));
		File[] srcSubDirs = srcDir.listFiles();
		File srcSubDir = null;

		// Chose a subdirectory randomly.
		while (srcSubDir == null || !srcSubDir.isDirectory())
		{

			srcSubDir = srcSubDirs[new Random().nextInt(srcSubDirs.length)];

		}

		// Loop through source directory.
		for (File srcGrp : srcSubDir.listFiles())
		{

			if (srcGrp.isFile())
			{

				// Copy file directly to destination.
				log("Copying " + srcGrp.getAbsolutePath() + " -> " + destDir.getAbsolutePath());
				Files.copy(srcGrp.toPath(), Paths.get(destDir.getAbsolutePath() + "\\" + srcGrp.getName()));

			}
			else if (srcGrp.isDirectory())
			{

				// Copy one random file from the directory to destination.
				File[] srcFiles = srcGrp.listFiles();
				File srcFile = srcFiles[new Random().nextInt(srcFiles.length)];
				log("Copying " + srcFile.getAbsolutePath() + " -> " + destDir.getAbsolutePath());
				Files.copy(srcFile.toPath(), Paths.get(destDir.getAbsolutePath() + "\\" + srcFile.getName()));

				// Shuffle group directory.
				File newSrcGrp = new File(srcSubDir.getAbsolutePath() + "\\" + (char) (new Random().nextInt(26) + 'A') + System.currentTimeMillis());
				log("Renaming " + srcGrp.getAbsolutePath() + " -> " + newSrcGrp.getAbsolutePath());
				srcGrp.renameTo(newSrcGrp);
				Thread.sleep(2);

			}

		}

		// Shuffle source subdirectory.
		File newSrcSubDir = new File(srcDir.getAbsolutePath() + "\\" + Instant.now().getEpochSecond());
		log("Renaming " + srcSubDir.getAbsolutePath() + " -> " + newSrcSubDir.getAbsolutePath());
		srcSubDir.renameTo(newSrcSubDir);

		// Save properties file.
		PROP.store(new FileOutputStream(PROP_FILE_NAME), null);

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
