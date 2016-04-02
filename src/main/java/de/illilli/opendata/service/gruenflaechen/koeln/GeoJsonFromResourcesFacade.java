package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import de.illilli.opendata.service.Facade;

/**
 * Read from Resources Folder an return as GeoJson String. This is for fasten up
 * the system on slow backends.
 * 
 */
public class GeoJsonFromResourcesFacade implements Facade {

	private static final Logger logger = Logger.getLogger(GeoJsonFromResourcesFacade.class);
	private String json = null;

	public GeoJsonFromResourcesFacade() throws MismatchedDimensionException, NoSuchAuthorityCodeException, IOException,
			FactoryException, TransformException {
		InputStream inputStream = this.getClass().getResourceAsStream("/flaechen.json");
		logger.info("read '/flaechen.json'");
		json = IOUtils.toString(inputStream);
	}

	public GeoJsonFromResourcesFacade(int typ) throws MismatchedDimensionException, NoSuchAuthorityCodeException,
			IOException, FactoryException, TransformException {
		InputStream inputStream = this.getClass().getResourceAsStream("/" + typ + ".json");
		logger.info("read '/" + typ + ".json'");
		json = IOUtils.toString(inputStream);
	}

	@Override
	public String getJson() {
		return json;
	}

}
