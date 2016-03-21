package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.IOException;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.opengis.filter.Filter;

public class ObjekttypFilter {

	private SimpleFeatureCollection features;
	private SimpleFeatureSource featureSource;

	/**
	 * @deprecated
	 * @return
	 */
	@Deprecated
	public ObjekttypFilter(Map<String, Object> params, String code) throws IOException, CQLException {

		DataStore store = DataStoreFinder.getDataStore(params);
		SimpleFeatureSource featureSource = store.getFeatureSource(store.getTypeNames()[0]);

		Filter filter = CQL.toFilter("Objekttyp > -1 ");
		features = featureSource.getFeatures(filter);

	}

	public ObjekttypFilter(SimpleFeatureSource featureSource) {
		this.featureSource = featureSource;
	}

	/**
	 * @deprecated
	 * @return
	 */
	@Deprecated
	public SimpleFeatureCollection getSimpleFeatureCollection() {
		return features;
	}

	public SimpleFeatureCollection getSimpleFeatureCollection(FlaechentypEnum flaechentyp)
			throws CQLException, IOException {
		Filter filter = CQL.toFilter(flaechentyp.cqlPredicate());
		return featureSource.getFeatures(filter);
	}
}
