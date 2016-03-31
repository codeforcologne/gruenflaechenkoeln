package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import de.illilli.opendata.service.Config;

public class GeoToolsGeoJsonTransformerTest {

	private URL url;
	private Map<String, Object> params;
	private String code = Config.getProperty("epsg.code");
	private SimpleFeatureSource featureSource;

	@Before
	public void setUp() throws Exception {
		url = new File("./src/test/resources/objekte.shp").toURI().toURL();
		params = new HashMap<String, Object>();
		params.put("url", url);
		params.put("create spatial index", false);
		params.put("memory mapped buffer", false);
		params.put("charset", "UTF-8");
		DataStore store = DataStoreFinder.getDataStore(params);
		featureSource = store.getFeatureSource(store.getTypeNames()[0]);
	}

	@Test
	public void testForFeatureCollection() throws IOException, MismatchedDimensionException,
			NoSuchAuthorityCodeException, FactoryException, TransformException {
		CoordinateTransformer ccr = new CoordinateTransformer(featureSource);
		GeoJsonTransformer transformer = new GeoToolsGeoJsonTransformer(ccr.transform());
		String json = transformer.getJson();
		System.out.println(json);
		Assert.assertTrue(json.contains("FeatureCollection"));
	}

	@Test
	public void testForCrs() throws IOException, MismatchedDimensionException, NoSuchAuthorityCodeException,
			FactoryException, TransformException {
		CoordinateTransformer ccr = new CoordinateTransformer(featureSource);
		GeoJsonTransformer transformer = new GeoToolsGeoJsonTransformer(ccr.transform());
		String json = transformer.getJson();
		Assert.assertTrue(json.contains("EPSG:3044"));

	}
}
