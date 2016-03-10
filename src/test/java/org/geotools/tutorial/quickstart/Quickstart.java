package org.geotools.tutorial.quickstart;

import java.io.File;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.data.JFileDataStoreChooser;

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
		File initialDir = new File("./src/test/resources");
		File file = JFileDataStoreChooser.showOpenFile("shp", initialDir, null);
		if (file == null) {
			return;
		}

		FileDataStore store = FileDataStoreFinder.getDataStore(file);
		SimpleFeatureSource featureSource = store.getFeatureSource();

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