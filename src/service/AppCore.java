package service;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import data_model.Cam;
import storage.Storage;
import storage.StorageFactory;

public class AppCore implements Job {

	private static Logger jlog = Logger.getLogger(AppCore.class);

	final Storage storageDao = StorageFactory.getInstance().getStorage();

	public AppCore() {
	}

	private void process() throws Exception {
		List<Cam> cams = storageDao.getCamList();
		for (Cam cam : cams) {
			jlog.info("Download für camid: " + cam.getId() + " gestartet");
			DownloadForCam dl = new DownloadForCam(cam, storageDao);
			new Thread(dl).start();
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			AppCore core = new AppCore();
			core.process();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
