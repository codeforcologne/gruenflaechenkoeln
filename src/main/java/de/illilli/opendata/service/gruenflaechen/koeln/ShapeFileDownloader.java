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

	private URL url;

	public ShapeFileDownloader() throws IOException {
		downloadFile();
	}

	void downloadFile() throws IOException {

		URL url = new URL(Config.getProperty("gruenobjekte.koeln"));
		String workingDirectory = System.getProperty("java.io.tmpdir")
				+ Config.getProperty("gruenobjekte.working.directory") + File.pathSeparatorChar;
		File file = new File(workingDirectory + Config.getProperty("gruenobjekte.file.name"));
		FileUtils.copyURLToFile(url, file);

		BufferedOutputStream dest = null;
		FileInputStream fileInputStream = new FileInputStream(
				System.getProperty("java.io.tmpdir") + Config.getProperty("gruenobjekte.file.name"));
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
		File objekteShpFile = new File(workingDirectory + Config.getProperty("gruenobjekte.working.file"));

		logger.info(objekteShpFile.getAbsolutePath());
		this.url = objekteShpFile.toURI().toURL();
	}

	public URL getUrl() {
		return this.url;
	}

}
