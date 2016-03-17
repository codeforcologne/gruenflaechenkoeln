package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.illilli.opendata.service.Facade;

public class GeoJsonFacade implements Facade {

	private String json;
	private Map<String, Object> params;
	private String code = "EPSG:4326";

	public GeoJsonFacade() throws MismatchedDimensionException, NoSuchAuthorityCodeException, IOException,
			FactoryException, TransformException {
		this(getUrlToShapeFile());
	}

	public GeoJsonFacade(URL url) throws MismatchedDimensionException, NoSuchAuthorityCodeException, IOException,
			FactoryException, TransformException {
		params = new HashMap<String, Object>();
		params.put("url", url);
		params.put("create spatial index", false);
		params.put("memory mapped buffer", false);
		params.put("charset", "UTF-8");

		CoordinateTransformer coordinateTransformer = new CoordinateTransformer(params, code);
		Shape2GeoJsonTransformer shape2GeoJsonTransformer = new Shape2GeoJsonTransformer(
				coordinateTransformer.getnewProjectionCollection());
		json = shape2GeoJsonTransformer.getJson();
	}

	/**
	 * Do not use this. File path not working!
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	@Deprecated
	static URL getUrlToShapeFile() throws MalformedURLException {
		File file = new File("./src/main/resources/objekte.shp");
		System.out.println("###: " + file.getAbsolutePath());
		return file.toURI().toURL();
	}

	@Override
	public String getJson() throws JsonProcessingException {
		return json;
	}

}
