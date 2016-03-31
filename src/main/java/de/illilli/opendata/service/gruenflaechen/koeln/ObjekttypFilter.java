package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.opengis.filter.Filter;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

public class ObjekttypFilter {

	private SimpleFeatureCollection features;
	private SimpleFeatureSource featureSource;

	public ObjekttypFilter(SimpleFeatureSource featureSource) {
		this.featureSource = featureSource;
	}

	public SimpleFeatureCollection getSimpleFeatureCollection(FlaechentypEnum flaechentyp)
			throws CQLException, IOException, MismatchedDimensionException, NoSuchAuthorityCodeException,
			FactoryException, TransformException {
		Filter filter = CQL.toFilter(flaechentyp.cqlPredicate());
		CoordinateTransformer coordinateTransformer = new CoordinateTransformer(featureSource);
		return featureSource.getFeatures(filter);
	}
}
