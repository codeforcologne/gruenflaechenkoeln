package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import de.illilli.opendata.service.Config;

public class ShapeFileDownloader {

	private static final Logger logger = Logger.getLogger(ShapeFileDownloader.class);

	private static ShapeFileDownloader downloader;
	private File objekteShpFile;
	private String workingDirectory = System.getProperty("java.io.tmpdir")
			+ Config.getProperty("gruenobjekte.working.directory") + File.separator;

	private ShapeFileDownloader() throws IOException {
		objekteShpFile = new File(workingDirectory + Config.getProperty("gruenobjekte.working.file"));
		if (objekteShpFile == null || !objekteShpFile.exists()) {
			downloadFile();
			unzipFile();
			objekteShpFile = new File(workingDirectory + Config.getProperty("gruenobjekte.working.file"));
			logger.info(objekteShpFile.getAbsolutePath());
		}
	}

	public static ShapeFileDownloader getInstance() throws IOException {
		if (downloader == null) {
			downloader = new ShapeFileDownloader();
		}
		return downloader;
	}

	void downloadFile() throws IOException {

		if (!objekteShpFile.exists()) {
			URL url = new URL(Config.getProperty("gruenobjekte.koeln"));
			logger.info(workingDirectory);
			File file = new File(workingDirectory + Config.getProperty("gruenobjekte.file.name"));
			FileUtils.copyURLToFile(url, file);
			logger.info(file.getAbsolutePath());
		}
	}

	void unzipFile() throws IOException {
		BufferedOutputStream dest = null;
		FileInputStream fileInputStream = new FileInputStream(
				workingDirectory + Config.getProperty("gruenobjekte.file.name"));
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fileInputStream));

		ZipEntry entry;
		int BUFFER = 2048;
		while ((entry = zis.getNextEntry()) != null) {
			int count;
			byte data[] = new byte[BUFFER];
			// write the files to the disk
			FileOutputStream fos = new FileOutputStream(workingDirectory + entry.getName());
			dest = new BufferedOutputStream(fos, BUFFER);
			while ((count = zis.read(data, 0, BUFFER)) != -1) {
				dest.write(data, 0, count);
			}
			dest.flush();
			dest.close();
		}
		zis.close();
	}

	boolean removeShpFile() {
		boolean fileRemoved = false;
		downloader.objekteShpFile = new File(workingDirectory + Config.getProperty("gruenobjekte.working.file"));
		if (downloader.objekteShpFile.exists()) {
			File workingDir = new File(workingDirectory);
			if (workingDir.isDirectory()) {
				String[] fileList = workingDir.list();
				// delete contents of directory first.
				for (String fileName : fileList) {
					boolean deleted = new File(workingDirectory + fileName).delete();
					logger.info(fileName + " deleted: " + deleted);
				}
				// remove static Content
				downloader.objekteShpFile = null;
				// delete directory itself.
				fileRemoved = workingDir.delete();
			}
		}
		return fileRemoved;
	}

	public URL getUrl() throws IOException {
		if (objekteShpFile == null || !objekteShpFile.exists()) {
			downloader = new ShapeFileDownloader();
		}
		return downloader.objekteShpFile.toURI().toURL();
	}

}
