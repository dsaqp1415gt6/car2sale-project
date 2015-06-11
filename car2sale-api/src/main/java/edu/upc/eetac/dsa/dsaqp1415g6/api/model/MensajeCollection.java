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

public class MensajeCollection {
	@InjectLinks({
		@InjectLink(resource = AnuncioResource.class, style = Style.ABSOLUTE, rel = "create-mensaje", title = "Create mensaje", type = MediaType.MENSAJES_API_MENSAJE),
		@InjectLink(value = "/mensajes?before={before}", style = Style.ABSOLUTE, rel = "before", title = "Previous mensajes", type = MediaType.MENSAJES_API_MENSAJE_COLLECTION, bindings = { @Binding(name = "before", value = "${instance.oldestTimestamp}") }),
		@InjectLink(value = "/mensajes?after={after}", style = Style.ABSOLUTE, rel = "after", title = "Newest mensajes", type = MediaType.MENSAJES_API_MENSAJE_COLLECTION, bindings = { @Binding(name = "after", value = "${instance.newestTimestamp}") }) })
	private List<Link> links;
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}


	private List<Mensaje> mensajes;
	private long newestTimestamp;
	private long oldestTimestamp;
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
	
	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	
	public void addMensaje(Mensaje mensaje){
		mensajes.add(mensaje);
	}

		
		public MensajeCollection() {
			super();
			mensajes = new ArrayList<>();
		}
}
