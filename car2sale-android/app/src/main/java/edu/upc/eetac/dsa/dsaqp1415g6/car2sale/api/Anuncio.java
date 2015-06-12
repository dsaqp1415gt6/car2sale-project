package edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api;


import java.util.HashMap;
import java.util.Map;

public class Anuncio {
    private String idanuncio;
    private String contador;
    private String username;
    private String titulo;
    private String descripcion;
    private String marca;
    private String modelo;
    private String km;
    private String precio;
    private String provincia;
    private long last_modified;
    private long creation_timestamp;
    private String ETag;
    private Map<String, Link> links = new HashMap<String, Link>();

    public String getIdanuncio() {
        return idanuncio;
    }

    public void setIdanuncio(String idanuncio) {
        this.idanuncio = idanuncio;
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

    public String getContador() {
        return contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getETag() {
        return ETag;
    }

    public void setETag(String ETag) {
        this.ETag = ETag;
    }


    public Map<String, Link> getLinks() {
        return links;
    }
}
