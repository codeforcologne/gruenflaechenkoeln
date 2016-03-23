package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.IOException;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.opengis.filter.Filter;

public class ObjekttypFilter {

	private SimpleFeatureCollection features;
	private SimpleFeatureSource featureSource;

	public ObjekttypFilter(SimpleFeatureSource featureSource) {
		this.featureSource = featureSource;
	}

	public SimpleFeatureCollection getSimpleFeatureCollection(FlaechentypEnum flaechentyp)
			throws CQLException, IOException {
		Filter filter = CQL.toFilter(flaechentyp.cqlPredicate());
		return featureSource.getFeatures(filter);
	}
}
