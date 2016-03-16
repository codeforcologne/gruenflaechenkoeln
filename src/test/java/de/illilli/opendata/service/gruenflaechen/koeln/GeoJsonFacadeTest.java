package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.junit.Before;
import org.junit.Test;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

public class GeoJsonFacadeTest {

	private Map<String, Object> params;
	private String code = "EPSG:4326";

	@Before
	public void setUp() throws Exception {
		File file = new File("./src/test/resources/objekte.shp");
		params = new HashMap<String, Object>();
		params.put("url", file.toURI().toURL());
		params.put("create spatial index", false);
		params.put("memory mapped buffer", false);
		params.put("charset", "UTF-8");
	}

	@Test
	public void test() throws MismatchedDimensionException, NoSuchAuthorityCodeException, IOException, FactoryException,
			TransformException {
		CoordinateTransformer ccr = new CoordinateTransformer(params, code);
		SimpleFeatureCollection simpleFeatureCollection = ccr.getnewProjectionCollection();
		GeoJsonFacade transformer = new GeoJsonFacade(simpleFeatureCollection);
		String json = transformer.getJson();
		// System.out.println(json);
	}

}
