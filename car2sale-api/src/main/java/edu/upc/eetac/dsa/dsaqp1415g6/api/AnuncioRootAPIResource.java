package edu.upc.eetac.dsa.dsaqp1415g6.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import edu.upc.eetac.dsa.dsaqp1415g6.api.model.AnunciosRootAPI;
@Path("/")
public class AnuncioRootAPIResource {
	@GET
	public AnunciosRootAPI getRootAPI() {
		AnunciosRootAPI api = new AnunciosRootAPI();
		return api;
	}
}
