package edu.upc.eetac.dsa.dsaqp1415g6.api.model;

import java.util.List;

import javax.ws.rs.core.Link;
 





import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.dsaqp1415g6.api.FavoritoResource;
import edu.upc.eetac.dsa.dsaqp1415g6.api.MensajeResource;
import edu.upc.eetac.dsa.dsaqp1415g6.api.AnuncioResource;
import edu.upc.eetac.dsa.dsaqp1415g6.api.AnuncioRootAPIResource;
import edu.upc.eetac.dsa.dsaqp1415g6.api.MediaType;
import edu.upc.eetac.dsa.dsaqp1415g6.api.UserResource;

public class AnunciosRootAPI {
	@InjectLinks({
        @InjectLink(resource = AnuncioRootAPIResource.class, style = Style.ABSOLUTE, rel = "self anunciomark home", title = "Anuncio Root API"),
        @InjectLink(resource = AnuncioResource.class, style = Style.ABSOLUTE, rel = "collection", title = "Latest anuncios", type = MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION),
        @InjectLink(resource = AnuncioResource.class, style = Style.ABSOLUTE, rel = "create-anuncio", title = "Create new Anuncio", type = MediaType.ANUNCIOS_API_ANUNCIO),
        @InjectLink(resource = MensajeResource.class, style = Style.ABSOLUTE, rel = "mensajes", title = "Create new Mensaje", type = MediaType.MENSAJES_API_MENSAJE_COLLECTION),
        @InjectLink(resource = MensajeResource.class, style = Style.ABSOLUTE, rel = "create-mensaje", title = "Create new Mensaje", type = MediaType.MENSAJES_API_MENSAJE),
        @InjectLink(resource = FavoritoResource.class, style = Style.ABSOLUTE, rel = "favoritos", title = "Create new Favorito", type = MediaType.FAVORITOS_API_FAVORITO_COLLECTION),
        @InjectLink(resource = FavoritoResource.class, style = Style.ABSOLUTE, rel = "create-favorito", title = "Create new Favorito", type = MediaType.FAVORITOS_API_FAVORITO),
        @InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "login", title = "login", type = MediaType.ANUNCIOS_API_USER, method = "login"),})
	private List<Link> links;

public List<Link> getLinks() {
	return links;
}

public void setLinks(List<Link> links) {
	this.links = links;
}

}
