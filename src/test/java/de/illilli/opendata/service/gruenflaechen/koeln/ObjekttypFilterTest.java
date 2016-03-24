package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.cql2.CQLException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Geometry;

public class ObjekttypFilterTest {

	private URL url;
	private Map<String, Object> params;
	private String code = "EPSG:4326";

	@Before
	public void setUp() throws Exception {
		url = new File("./src/test/resources/objekte.shp").toURI().toURL();
		params = new HashMap<String, Object>();
		params.put("url", url);
		params.put("create spatial index", false);
		params.put("memory mapped buffer", false);
		params.put("charset", "UTF-8");
	}

	@Test
	public void testSpielplaetze() throws IOException, CQLException, MismatchedDimensionException,
			NoSuchAuthorityCodeException, FactoryException, TransformException {
		DataStore store = DataStoreFinder.getDataStore(params);
		SimpleFeatureSource featureSource = store.getFeatureSource(store.getTypeNames()[0]);

		ObjekttypFilter filter = new ObjekttypFilter(featureSource);
		SimpleFeatureCollection simpleFeatureCollection = filter.getSimpleFeatureCollection(FlaechentypEnum.SPIELPLATZ);
		SimpleFeatureIterator iterator = simpleFeatureCollection.features();

		while (iterator.hasNext()) {
			SimpleFeature feature = iterator.next();

			int expected = 4;
			int actual = (Integer) feature.getAttribute(3);
			Assert.assertEquals(expected, actual);
			// System.out.println(feature.getAttribute(2) + ": " +
			// feature.getAttribute(3) + ": " + feature.getAttribute(7)
			// + " - " + feature.getAttribute(2));

		}
	}

	@Test
	public void testIntegrity() throws IOException, CQLException {
		DataStore store = DataStoreFinder.getDataStore(params);
		SimpleFeatureSource featureSource = store.getFeatureSource(store.getTypeNames()[0]);

		SimpleFeatureCollection simpleFeatureCollection = featureSource.getFeatures();
		SimpleFeatureIterator iterator = simpleFeatureCollection.features();

		while (iterator.hasNext()) {
			SimpleFeature feature = iterator.next();
			Geometry geometry = (Geometry) feature.getDefaultGeometry();

			if (geometry != null && !geometry.isValid()) {
				System.out.println("Invalid Geoemtry: " + feature.getID() + "; GeometryType: "
						+ geometry.getGeometryType() + "; SRID: " + geometry.getSRID() + "; getCoordinate :"
						+ geometry.getCoordinate().toString());
			}
		}
		featureSource.getDataStore().dispose();

	}

}
