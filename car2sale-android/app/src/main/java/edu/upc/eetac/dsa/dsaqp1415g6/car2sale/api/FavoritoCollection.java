package edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FavoritoCollection {
    private List<Favorito> favoritos;
    private long newestTimestamp;
    private long oldestTimestamp;
    private Map<String, Link> links = new HashMap<String, Link>();
    public FavoritoCollection() {
        super();
        favoritos = new ArrayList<Favorito>();
    }

    public List<Favorito> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(List<Favorito> favoritos ) {
        this.favoritos = favoritos;
    }

    public void addFavorito(Favorito favorito) {
        favoritos.add(favorito);
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

    public Map<String, Link> getLinks() {
        return links;
    }
}
