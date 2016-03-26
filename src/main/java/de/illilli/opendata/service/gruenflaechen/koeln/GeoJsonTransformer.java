package de.illilli.opendata.service.gruenflaechen.koeln;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GeoJsonTransformer {
	String getJson() throws JsonProcessingException;
}
