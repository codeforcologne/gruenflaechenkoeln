package de.illilli.opendata.service.gruenflaechen.koeln;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.CoordinateFilter;

/**
 * <p>
 * invert geometry for geojson
 * </p>
 * <p>
 * <a href=
 * "http://stackoverflow.com/questions/27623620/how-to-swap-coordinates-of-jts-geom-geometry-object-from-lat-long-to-long-lat-i">
 * How to Swap Coordinates of jts.geom.Geometry object from Lat, Long to
 * Long,Lat in JTS</a>
 * </p>
 * <p>
 * Usage:
 * </p>
 * 
 * <pre>
 * Geometry geometry = (Geometry) simpleFeature.getDefaultGeometry();
 * geometry.apply(new InvertCoordinateFilter());
 * </pre>
 *
 */
public class InvertCoordinateFilter implements CoordinateFilter {
	@Override
	public void filter(Coordinate coord) {
		double oldX = coord.x;
		coord.x = coord.y;
		coord.y = oldX;
	}
}
