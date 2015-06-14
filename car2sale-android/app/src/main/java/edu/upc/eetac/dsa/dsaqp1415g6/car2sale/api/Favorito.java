package edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Javi on 06/06/2015.
 */
public class Favorito {


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getETag() {
        return ETag;
    }

    public void setETag(String ETag) {
        this.ETag = ETag;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<String, Link> links) {
        this.links = links;
    }

    public String getIdfavorito() {
        return idfavorito;
    }

    public void setIdfavorito(String idfavorito) {
        this.idfavorito = idfavorito;
    }

    public String getIdanuncio() {
        return idanuncio;
    }

    public void setIdanuncio(String idanuncio) {
        this.idanuncio = idanuncio;
    }

    String idfavorito;
    String idanuncio;
    String username;
    private long last_modified;
    private long creation_timestamp;
    private String ETag;
    private Map<String, Link> links = new HashMap<String, Link>();
}
