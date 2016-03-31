package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.filter.text.cql2.CQLException;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.fasterxml.jackson.core.JsonProcessingException;

public class GeoJsonFacadeWithFilter extends GeoJsonFacade {

	private static final Logger logger = Logger.getLogger(GeoJsonFacadeWithFilter.class);

	private Integer id;

	public GeoJsonFacadeWithFilter(Integer id) throws MismatchedDimensionException, NoSuchAuthorityCodeException,
			IOException, FactoryException, TransformException {
		this.id = id;
		setFeatureSource(new ShapeFileDownloader().getUrl());
	}

	@Override
	public String getJson() throws JsonProcessingException {
		String json = "";
		try {
			ObjekttypFilter filter = new ObjekttypFilter(featureSource);
			SimpleFeatureCollection simpleFeatureCollection = filter
					.getSimpleFeatureCollection(FlaechentypEnum.getById(this.id));
			CoordinateTransformer coordinateTransformer = new CoordinateTransformer(featureSource);
			GeoJsonTransformer shape2GeoJsonTransformer = new GeoToolsGeoJsonTransformer(
					coordinateTransformer.transform(simpleFeatureCollection));
			json = shape2GeoJsonTransformer.getJson();
		} catch (CQLException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} catch (MismatchedDimensionException e) {
			logger.error(e);
		} catch (NoSuchAuthorityCodeException e) {
			logger.error(e);
		} catch (FactoryException e) {
			logger.error(e);
		} catch (TransformException e) {
			logger.error(e);
		}
		return json;
	}

}
