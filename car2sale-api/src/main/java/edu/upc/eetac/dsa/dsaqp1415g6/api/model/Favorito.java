package edu.upc.eetac.dsa.dsaqp1415g6.api.model;

import java.util.List;

import javax.ws.rs.core.Link;





import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.dsaqp1415g6.api.FavoritoResource;
import edu.upc.eetac.dsa.dsaqp1415g6.api.MediaType;

public class Favorito {
	@InjectLinks({
		@InjectLink(resource = FavoritoResource.class, style = Style.ABSOLUTE, rel = "favoritos", title = "Latest favoritos", type = MediaType.FAVORITOS_API_FAVORITO_COLLECTION),
		@InjectLink(resource = FavoritoResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Favorito", type = MediaType.FAVORITOS_API_FAVORITO, method = "getFavorito", bindings = @Binding(name = "idfavorito", value = "${instance.idfavorito}")) })
private List<Link> links;
	
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public int getIdanuncio() {
		return idanuncio;
	}
	public void setIdanuncio(int idanuncio) {
		this.idanuncio = idanuncio;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	int idanuncio;
	String username;
	private long last_modified;
	public long getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(long last_modified) {
		this.last_modified = last_modified;
	}
	public long getCreation_timestamp() {
		return creation_timestamp;
	}
	public void setCreation_timestamp(long creation_timestamp) {
		this.creation_timestamp = creation_timestamp;
	}
	private long creation_timestamp;

}
