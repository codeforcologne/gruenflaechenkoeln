package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.illilli.opendata.service.Config;
import de.illilli.opendata.service.Facade;

public class GeoJsonFacade implements Facade {

	private static final Logger logger = Logger.getLogger(GeoJsonFacade.class);

	private String json;
	private Map<String, Object> params;

	public GeoJsonFacade() throws MismatchedDimensionException, NoSuchAuthorityCodeException, IOException,
			FactoryException, TransformException {
		this(new ShapeFileDownloader().getUrl());
	}

	public GeoJsonFacade(URL url) throws MismatchedDimensionException, NoSuchAuthorityCodeException, IOException,
			FactoryException, TransformException {
		params = new HashMap<String, Object>();
		params.put("url", url);
		params.put("create spatial index", false);
		params.put("memory mapped buffer", false);
		params.put("charset", "UTF-8");

		CoordinateTransformer coordinateTransformer = new CoordinateTransformer(params,
				Config.getProperty("epsg.code"));
		Shape2GeoJsonTransformer shape2GeoJsonTransformer = new Shape2GeoJsonTransformer(
				coordinateTransformer.getnewProjectionCollection());
		json = shape2GeoJsonTransformer.getJson();
	}

	@Override
	public String getJson() throws JsonProcessingException {
		return json;
	}

}
