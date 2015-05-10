package edu.upc.eetac.dsa.dsaqp1415g6.api.model;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;


import edu.upc.eetac.dsa.dsaqp1415g6.api.MensajeResource;
import edu.upc.eetac.dsa.dsaqp1415g6.api.MediaType;
public class Mensaje {
	@InjectLinks({
		@InjectLink(resource = MensajeResource.class, style = Style.ABSOLUTE, rel = "mensajes", title = "Latest mensajes", type = MediaType.MENSAJES_API_MENSAJE_COLLECTION),
		@InjectLink(resource = MensajeResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Mensaje", type = MediaType.MENSAJES_API_MENSAJE, method = "getMensaje", bindings = @Binding(name = "idmensaje", value = "${instance.idmensaje}")) })
private List<Link> links;
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	private String usuarioenvia;
	public String getUsuarioenvia() {
		return usuarioenvia;
	}
	public void setUsuarioenvia(String usuarioenvia) {
		this.usuarioenvia = usuarioenvia;
	}
	
	public String getUsuariorecibe() {
		return usuariorecibe;
	}
	public void setUsuariorecibe(String usuariorecibe) {
		this.usuariorecibe = usuariorecibe;
	}
	public int getAnuncio() {
		return anuncio;
	}
	public void setAnuncio(int anuncio) {
		this.anuncio = anuncio;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
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
	
	public int getIdmensaje() {
		return idmensaje;
	}
	public void setIdmensaje(int idmensaje) {
		this.idmensaje = idmensaje;
	}
	private int idmensaje;
	private String usuariorecibe;
	private int anuncio;
	private String mensaje;
	private long last_modified;
	private long creation_timestamp;

}
