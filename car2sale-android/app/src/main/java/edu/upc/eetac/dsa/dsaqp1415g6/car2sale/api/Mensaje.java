package edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api;


import java.util.HashMap;
import java.util.Map;

public class Mensaje {
    public String getIdmensaje() {
        return idmensaje;
    }

    public void setIdmensaje(String idmensaje) {
        this.idmensaje = idmensaje;
    }

    public String getUsuariorecibe() {
        return usuariorecibe;
    }

    public void setUsuariorecibe(String usuariorecibe) {
        this.usuariorecibe = usuariorecibe;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(String anuncio) {
        this.anuncio = anuncio;
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

    private String idmensaje;
    private String usuariorecibe;

    public String getUsuarioenvia() {
        return usuarioenvia;
    }

    public void setUsuarioenvia(String usuarioenvia) {
        this.usuarioenvia = usuarioenvia;
    }

    private String usuarioenvia;
    private String anuncio;
    private String mensaje;
    private long last_modified;
    private long creation_timestamp;
    private String ETag;
    private Map<String, Link> links = new HashMap<String, Link>();
}
