package service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import data_model.Cam;
import data_model.Picture;
import storage.Storage;
import utils.MyPictureShrink;
import utils.StorageFormatter;

public class DownloadForCam implements Runnable {
	private static Logger jlog = Logger.getLogger(AppCore.class);
	private Cam cam;
	final Storage storageDao;

	public DownloadForCam(Cam cam, Storage storageDao) {
		this.storageDao = storageDao;
		this.cam = cam;
	}

	@Override
	public void run() {
		try {
			Calendar calendar = Calendar.getInstance();
			Date dateNow = new Date(calendar.getTime().getTime());
			File targetFile = StorageFormatter.getStorageLocation(dateNow, cam.getId());
			URL url = cam.getUrl();

			// Download
			FileUtils.copyURLToFile(url, targetFile, 60, 60);
			jlog.info("Download für camid: " + cam.getId() + " erfolgreich");
			// Thumb erstellen
			MyPictureShrink.createThumbnail(targetFile);
			jlog.info("Thumb für camid: " + cam.getId() + " erfolgreich erstellt");
			storageDao
					.addPic(new Picture(cam.getId(), dateNow, StorageFormatter.getRelativePath(dateNow, cam.getId())));
			jlog.info("Bild für camid: " + cam.getId() + " in db Hinzugefügt");

		} catch (IOException e) {
			jlog.error("Download für camid: " + cam.getId() + " schlug Fehl");
			jlog.error(e.getMessage());
		}
	}

}
