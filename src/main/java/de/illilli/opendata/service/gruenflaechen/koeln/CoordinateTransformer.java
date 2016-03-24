package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.IOException;

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
	private SimpleFeatureSource featureSource;
	private SimpleFeatureCollection featureCollection;
	private String code;

	public CoordinateTransformer(final SimpleFeatureSource featureSource, String code) throws IOException,
			NoSuchAuthorityCodeException, FactoryException, MismatchedDimensionException, TransformException {
		this.featureSource = featureSource;
		this.code = code;
		featureCollection = featureSource.getFeatures();
	}

	public SimpleFeatureCollection transform(final SimpleFeatureCollection featureCollection)
			throws MismatchedDimensionException, NoSuchAuthorityCodeException, IOException, FactoryException,
			TransformException {
		this.featureCollection = featureCollection;
		transform();
		return newProjectionCollection;
	}

	public SimpleFeatureCollection transform() throws IOException, NoSuchAuthorityCodeException, FactoryException,
			MismatchedDimensionException, TransformException {

		SimpleFeatureType schema = featureSource.getSchema();

		CoordinateReferenceSystem dataCRS = schema.getCoordinateReferenceSystem();
		CoordinateReferenceSystem newCRS = CRS.decode(code, false);

		boolean lenient = true; // allow for some error due to different datums
		MathTransform transform = CRS.findMathTransform(dataCRS, newCRS, lenient);

		newProjectionCollection = new ListFeatureCollection(featureCollection);
		SimpleFeatureIterator iterator = newProjectionCollection.features();

		while (iterator.hasNext()) {

			SimpleFeature feature = iterator.next();
			Geometry geometry = (Geometry) feature.getDefaultGeometry();
			feature.setDefaultGeometry(JTS.transform(geometry, transform));

		}
		return newProjectionCollection;

	}

}
