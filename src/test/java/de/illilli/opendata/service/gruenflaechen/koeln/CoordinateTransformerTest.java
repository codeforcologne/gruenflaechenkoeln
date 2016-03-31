package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import de.illilli.opendata.service.Config;

public class CoordinateTransformerTest {

	private Map<String, Object> params;
	private String code = Config.getProperty("epsg.code");
	SimpleFeatureSource featureSource;

	@Before
	public void setUp() throws Exception {
		File file = new File("./src/test/resources/objekte.shp");
		params = new HashMap<String, Object>();
		params.put("url", file.toURI().toURL());
		params.put("create spatial index", false);
		params.put("memory mapped buffer", false);
		params.put("charset", "UTF-8");
		DataStore store = DataStoreFinder.getDataStore(params);
		featureSource = store.getFeatureSource(store.getTypeNames()[0]);

	}

	@Test
	public void testSizeAll() throws IOException, NoSuchAuthorityCodeException, FactoryException,
			MismatchedDimensionException, TransformException {
		CoordinateTransformer ccr = new CoordinateTransformer(featureSource);
		SimpleFeatureCollection sfc = ccr.transform();
		int expected = 2802;
		int actual = sfc.size();
		Assert.assertEquals(expected, actual);
	}

}
