package utils;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class StorageFormatter {

	private static final String storageFolder = "/tmp/cams";

	public static File getStorageLocation(Date date, long camId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/HH_mm_ss");
		StringBuilder text = new StringBuilder();
		text.append(storageFolder);
		text.append('/');
		text.append(camId);
		text.append('/');
		text.append(format.format(date));
		text.append(".jpg");
		return new File(text.toString());

	}

	public static String getRelativePath(Date date, long camId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/HH_mm_ss");
		StringBuilder text = new StringBuilder();
		text.append(camId);
		text.append('/');
		text.append(format.format(date));
		text.append(".jpg");
		return text.toString();

	}
}
