package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShapeFileDownloaderTest {

	private static final Logger logger = Logger.getLogger(ShapeFileDownloaderTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetUrl() throws IOException {
		ShapeFileDownloader downloader = ShapeFileDownloader.getInstance();
		// remove shapeFile first
		boolean shpfileRemoved = downloader.removeShpFile();
		logger.info("shapefile removed: '" + shpfileRemoved + "'");
		URL url = downloader.getUrl();
		Assert.assertTrue(url.toString().contains("objekte.shp"));
	}

}
