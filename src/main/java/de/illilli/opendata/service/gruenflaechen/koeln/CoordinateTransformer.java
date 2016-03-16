package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.IOException;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.collection.ListFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.jts.JTS;
import org.geotools.referencing.CRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Geometry;

public class CoordinateTransformer {

	private ListFeatureCollection newProjectionCollection;

	public CoordinateTransformer(Map<String, Object> params, String code) throws IOException,
			NoSuchAuthorityCodeException, FactoryException, MismatchedDimensionException, TransformException {

		transform(params, code);

	}

	void transform(Map<String, Object> params, String code) throws IOException, NoSuchAuthorityCodeException,
			FactoryException, MismatchedDimensionException, TransformException {

		DataStore store = DataStoreFinder.getDataStore(params);
		SimpleFeatureSource featureSource = store.getFeatureSource(store.getTypeNames()[0]);
		SimpleFeatureType schema = featureSource.getSchema();

		CoordinateReferenceSystem dataCRS = schema.getCoordinateReferenceSystem();
		CoordinateReferenceSystem newCRS = CRS.decode(code, false);

		final SimpleFeatureCollection featureCollection = featureSource.getFeatures();

		boolean lenient = true; // allow for some error due to different datums
		MathTransform transform = CRS.findMathTransform(dataCRS, newCRS, lenient);

		newProjectionCollection = new ListFeatureCollection(featureCollection);
		SimpleFeatureIterator iterator = newProjectionCollection.features();

		while (iterator.hasNext()) {

			SimpleFeature feature = iterator.next();
			Geometry geometry = (Geometry) feature.getDefaultGeometry();
			feature.setDefaultGeometry(JTS.transform(geometry, transform));

		}
	}

	public SimpleFeatureCollection getnewProjectionCollection() {
		return newProjectionCollection;
	}

}
