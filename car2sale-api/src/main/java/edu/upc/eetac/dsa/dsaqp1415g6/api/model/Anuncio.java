package edu.upc.eetac.dsa.dsaqp1415g6.api.model;
import java.util.List;

import javax.ws.rs.core.Link;
 



import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.dsaqp1415g6.api.AnuncioResource;
import edu.upc.eetac.dsa.dsaqp1415g6.api.MediaType;
public class Anuncio {
	@InjectLinks({
		@InjectLink(resource = AnuncioResource.class, style = Style.ABSOLUTE, rel = "anuncios", title = "Latest anuncios", type = MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION),
		@InjectLink(resource = AnuncioResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Anuncio", type = MediaType.ANUNCIOS_API_ANUNCIO, method = "getAnuncio", bindings = @Binding(name = "idanuncio", value = "${instance.idanuncio}")) })
private List<Link> links;

	public int getIdanuncio() {
		return idanuncio;
	}
	public void setIdanuncio(int idanuncio) {
		this.idanuncio = idanuncio;
	}
	public int getContador() {
		return contador;
	}
	public void setContador(int contador) {
		this.contador = contador;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public int getKm() {
		return km;
	}
	public void setKm(int km) {
		this.km = km;
	}
	public int getPrecio() {
		return precio;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
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
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	private int idanuncio;
	private int contador;
	private String username;
	private String titulo;
	private String descripcion;
	private String marca;
	private String modelo;
	private int km;
	private int precio;
	private String provincia;
	private long last_modified;
	private long creation_timestamp;
	private String filename;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	private String imageURL;
	

}
