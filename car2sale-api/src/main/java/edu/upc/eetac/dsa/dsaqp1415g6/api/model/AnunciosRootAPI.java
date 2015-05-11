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

public class AnunciosRootAPI {
	@InjectLinks({
        @InjectLink(resource = AnuncioRootAPIResource.class, style = Style.ABSOLUTE, rel = "self anunciomark home", title = "Anuncio Root API"),
        @InjectLink(resource = FavoritoResource.class, style = Style.ABSOLUTE, rel = "favoritos", title = "Create new Favorito", type = MediaType.FAVORITOS_API_FAVORITO_COLLECTION),
        @InjectLink(resource = FavoritoResource.class, style = Style.ABSOLUTE, rel = "create-favorito", title = "Create new Favorito", type = MediaType.FAVORITOS_API_FAVORITO)})
	private List<Link> links;

public List<Link> getLinks() {
	return links;
}

public void setLinks(List<Link> links) {
	this.links = links;
}

}
