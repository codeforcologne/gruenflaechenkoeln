package de.illilli.opendata.service.gruenflaechen.koeln;

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
 */

public class Shape2GeoJsonTransformer {

	private FeatureCollection geojsonFeatureCollection = new FeatureCollection();

	public Shape2GeoJsonTransformer(SimpleFeatureCollection simpleFeatureCollection) {

		SimpleFeatureIterator iterator = simpleFeatureCollection.features();
		Feature geojsonFeature = null;

		while (iterator.hasNext()) {

			geojsonFeature = new Feature();
			SimpleFeature simpleFeature = iterator.next();

			Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
			geometry.apply(new InvertCoordinateFilter());

			org.geojson.MultiPolygon geojsonMulitPoligon = new org.geojson.MultiPolygon();

			String id = Integer.toString((Integer) simpleFeature.getAttribute(1));
			geojsonFeature.setId(id);
			Map<String, Object> properties = new Hashtable<String, Object>();
			properties.put("Name", simpleFeature.getAttribute(2));
			properties.put("Objekttyp", simpleFeature.getAttribute(3));
			properties.put("StrS", simpleFeature.getAttribute(4));
			properties.put("StrName", simpleFeature.getAttribute(5));
			properties.put("Shape_Area", simpleFeature.getAttribute(6));
			properties.put("Objekttyp_", simpleFeature.getAttribute(7));
			geojsonFeature.setProperties(properties);

			MultiPolygon multiPolygon = (MultiPolygon) simpleFeature.getAttribute(0);
			int numGeometries = multiPolygon.getNumGeometries();
			for (int n = 0; n < numGeometries; n++) {

				org.geojson.Polygon geojsonPolygon = new org.geojson.Polygon();
				Polygon polygon = (Polygon) multiPolygon.getGeometryN(n);
				Coordinate[] coordinates = polygon.getCoordinates();
				List<LngLatAlt> lngLatAltList = new ArrayList<>();
				boolean firstRun = true;
				LngLatAlt firstLngLatAlt = new LngLatAlt();
				LngLatAlt lngLatAlt = new LngLatAlt();
				for (Coordinate coordinate : coordinates) {
					if (firstRun) {
						firstLngLatAlt = new LngLatAlt(coordinate.x, coordinate.y);
						firstRun = false;
					}
					lngLatAlt = new LngLatAlt(coordinate.x, coordinate.y);
					lngLatAltList.add(lngLatAlt);
				}
				if (!lngLatAlt.equals(firstLngLatAlt)) {
					lngLatAltList.add(firstLngLatAlt);
				}
				geojsonPolygon.add(lngLatAltList);
				geojsonMulitPoligon.add(geojsonPolygon);
			}
			geojsonFeature.setGeometry(geojsonMulitPoligon);
			geojsonFeatureCollection.add(geojsonFeature);
		}

	}

	public String getJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(geojsonFeatureCollection);
	}

}
