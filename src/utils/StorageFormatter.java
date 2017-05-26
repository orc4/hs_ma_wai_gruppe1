package utils;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class StorageFormatter {

//	private static final String storageFolder = "/tmp/cams";
	
	private  static File getStoragePath(){		
		JNDIFactory jndiFactory = JNDIFactory.getInstance();
		File storageLocation=null;
		try {
			storageLocation = new File(jndiFactory.getEnvironmentAsString("projectPath")
					+ jndiFactory.getEnvironmentAsString("picturePath"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return storageLocation;
	}

	public static File getStorageLocation(Date date, long camId) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/HH_mm_ss");
		StringBuilder text = new StringBuilder();
		text.append(getStoragePath());
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
