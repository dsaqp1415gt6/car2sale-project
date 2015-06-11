package edu.upc.eetac.dsa.dsaqp1415g6.api;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import edu.upc.eetac.dsa.dsaqp1415g6.api.model.AnuncioError;
@Provider
public class WebApplicationExceptionMapper implements
ExceptionMapper<WebApplicationException> {
@Override
public Response toResponse(WebApplicationException exception) {
AnuncioError error = new AnuncioError(
		exception.getResponse().getStatus(), exception.getMessage());
return Response.status(error.getStatus()).entity(error)
		.type(MediaType.ANUNCIOS_API_ERROR).build();
}

}
