package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.LngLatAlt;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.opengis.feature.simple.SimpleFeature;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

import de.illilli.opendata.service.Facade;

public class GeoJsonFacade implements Facade {

	private FeatureCollection geojsonFeatureCollection = new FeatureCollection();
	private String json;

	/**
	 * <ul>
	 * <li>the_geom=0</li>
	 * <li>KLRID=1</li>
	 * <li>Name=2</li>
	 * <li>Objekttyp=3</li>
	 * <li>StrS=4</li>
	 * <li>StrName=5</li>
	 * <li>Shape_Area=6</li>
	 * <li>Objekttyp_=7</li>
	 * </ul>
	 * 
	 * @param simpleFeatureCollection
	 * @throws IOException
	 */
	public GeoJsonFacade(SimpleFeatureCollection simpleFeatureCollection) throws IOException {

		SimpleFeatureIterator iterator = simpleFeatureCollection.features();
		Feature geojsonFeature = null;

		while (iterator.hasNext()) {

			geojsonFeature = new Feature();
			SimpleFeature simpleFeature = iterator.next();

			Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
			geometry.apply(new InvertCoordinateFilter());

			org.geojson.MultiPolygon geojsonMulitPoligon = new org.geojson.MultiPolygon();

			System.out.println("1: " + simpleFeature.getAttribute(1));
			String id = (String) simpleFeature.getAttribute(1);
			geojsonFeature.setId(id);
			Map<String, Object> properties = new Hashtable<String, Object>();
			System.out.println("2: " + simpleFeature.getAttribute(2));
			properties.put("name", simpleFeature.getAttribute(2));
			System.out.println("3: " + simpleFeature.getAttribute(3));

			System.out.println("4: " + simpleFeature.getAttribute(4));
			System.out.println("5: " + simpleFeature.getAttribute(5));
			System.out.println("6: " + simpleFeature.getAttribute(6));
			System.out.println("7: " + simpleFeature.getAttribute(7));

			MultiPolygon multiPolygon = (MultiPolygon) simpleFeature.getAttribute(0);
			int numGeometries = multiPolygon.getNumGeometries();
			for (int n = 0; n < numGeometries; n++) {

				org.geojson.Polygon geojsonPolygon = new org.geojson.Polygon();
				Polygon polygon = (Polygon) multiPolygon.getGeometryN(n);
				Coordinate[] coordinates = polygon.getCoordinates();
				List<LngLatAlt> lngLatAltList = new ArrayList<>();
				for (Coordinate coordinate : coordinates) {
					LngLatAlt lngLatAlt = new LngLatAlt(coordinate.x, coordinate.y);
					lngLatAltList.add(lngLatAlt);
				}
				geojsonPolygon.add(lngLatAltList);
				geojsonMulitPoligon.add(geojsonPolygon);
			}
			geojsonFeature.setGeometry(geojsonMulitPoligon);
			geojsonFeatureCollection.add(geojsonFeature);
		}
	}

	@Override
	public String getJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(geojsonFeatureCollection);
	}

}
