package edu.upc.eetac.dsa.dsaqp1415g6.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;




import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.dsaqp1415g6.api.AnuncioResource;
import edu.upc.eetac.dsa.dsaqp1415g6.api.MediaType;


public class AnuncioCollection {
	@InjectLinks({
		@InjectLink(resource = AnuncioResource.class, style = Style.ABSOLUTE, rel = "create-anuncio", title = "Create anuncio", type = MediaType.ANUNCIOS_API_ANUNCIO),
		@InjectLink(value = "/anuncios?before={before}", style = Style.ABSOLUTE, rel = "before", title = "Anuncios anteriores", type = MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION, bindings = { @Binding(name = "before", value = "${instance.oldestTimestamp}") }),
		@InjectLink(value = "/anuncios?after={after}", style = Style.ABSOLUTE, rel = "after", title = "Anuncios nuevos", type = MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION, bindings = { @Binding(name = "after", value = "${instance.newestTimestamp}") }) })
	private List<Link> links;
	private List<Anuncio> anuncios;
	private long newestTimestamp;
	private long oldestTimestamp;
	private int primeranuncio;
	public int getPrimeranuncio() {
		return primeranuncio;
	}

	public void setPrimeranuncio(int primeranuncio) {
		this.primeranuncio = primeranuncio;
	}

	public int getUltimoanuncio() {
		return ultimoanuncio;
	}

	public void setUltimoanuncio(int ultimoanuncio) {
		this.ultimoanuncio = ultimoanuncio;
	}

	private int ultimoanuncio;
 
	
	public List<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(List<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}
	
	public void addAnuncio(Anuncio anuncio){
		anuncios.add(anuncio);
	}

		
		public AnuncioCollection() {
			super();
			anuncios = new ArrayList<>();
		}

		public long getNewestTimestamp() {
			return newestTimestamp;
		}

		public void setNewestTimestamp(long newestTimestamp) {
			this.newestTimestamp = newestTimestamp;
		}

		public long getOldestTimestamp() {
			return oldestTimestamp;
		}

		public void setOldestTimestamp(long oldestTimestamp) {
			this.oldestTimestamp = oldestTimestamp;
		}
		public List<Link> getLinks() {
			return links;
		}
	 
		public void setLinks(List<Link> links) {
			this.links = links;
		}

}
