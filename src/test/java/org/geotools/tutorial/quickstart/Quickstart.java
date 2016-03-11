package org.geotools.tutorial.quickstart;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.opengis.feature.simple.SimpleFeature;

/**
 * <p>
 * Source from <a href=
 * "http://docs.geotools.org/latest/userguide/tutorial/quickstart/maven.html">
 * GeoTools Maven Quickstart</a>
 * </p>
 * <p>
 * Prompts the user for a shapefile and displays the contents on the screen in a
 * map frame.
 * </p>
 * <p>
 * This is the GeoTools Quickstart application used in documentation and
 * tutorials.
 * </p>
 */
public class Quickstart {

	/**
	 * GeoTools Quickstart demo application. Prompts the user for a shapefile
	 * and displays its contents on the screen in a map frame
	 */
	public static void main(String[] args) throws Exception {
		// display a data store file chooser dialog for shapefiles
		File file = new File("./src/test/resources/objekte.shp");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("url", file.toURI().toURL());
		params.put("create spatial index", false);
		params.put("memory mapped buffer", false);
		params.put("charset", "UTF-8");

		DataStore store = DataStoreFinder.getDataStore(params);
		SimpleFeatureSource featureSource = store.getFeatureSource(store.getTypeNames()[0]);
		SimpleFeatureCollection featureCollection = featureSource.getFeatures();
		SimpleFeatureIterator iterator = featureCollection.features();

		while (iterator.hasNext()) {
			// copy the contents of each feature and transform the geometry
			SimpleFeature feature = iterator.next();
			// the_geom:MultiPolygon,KLRID:KLRID,Name:Name,Objekttyp:Objekttyp,StrS:StrS,StrName:StrName,Shape_Area:Shape_Area,Objekttyp_:Objekttyp_
			System.out.println(feature.toString());

			System.out.println("###---###");
		}

		// Create a map content and add our shapefile to it
		MapContent map = new MapContent();
		map.setTitle("Quickstart");

		Style style = SLD.createSimpleStyle(featureSource.getSchema());
		Layer layer = new FeatureLayer(featureSource, style);
		map.addLayer(layer);

		// Now display the map
		JMapFrame.showMap(map);
	}

}