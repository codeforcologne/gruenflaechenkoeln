package de.illilli.opendata.service.gruenflaechen.koeln;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.operation.TransformException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.illilli.opendata.service.Config;
import de.illilli.opendata.service.Facade;

@Path("/")
public class Service {

	private final static Logger logger = Logger.getLogger(Service.class);
	public static final String ENCODING = Config.getProperty("encoding");

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;

	/**
	 * <p>
	 * This service response with JSON only. Different formats are available.
	 * </p>
	 * 
	 * <p>
	 * Example
	 * <a href="http://localhost:8080/gruenflaechenkoeln/service/flaechen">
	 * /gruenflaechenkoeln/service/flaechen</a>
	 * </p>
	 * 
	 * <p>
	 * Example (geojson) <a href=
	 * "http://localhost:8080/gruenflaechenkoeln/service/flaechen?geojson">
	 * /gruenflaechenkoeln/service/flaechen?geojson</a>
	 * </p>
	 * 
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws SQLException
	 * @throws NamingException
	 * @throws MismatchedDimensionException
	 * @throws NoSuchAuthorityCodeException
	 * @throws FactoryException
	 * @throws TransformException
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/flaechen")
	public String getJson() throws JsonParseException, JsonMappingException, IOException, SQLException, NamingException,
			MismatchedDimensionException, NoSuchAuthorityCodeException, FactoryException, TransformException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		boolean geojson = request.getParameter("geojson") != null;
		Facade facade = null;
		if (geojson) {
			facade = new GeoJsonFacade();
		} else {
			facade = new JsonFacade();
		}

		return facade.getJson();
	}

}
