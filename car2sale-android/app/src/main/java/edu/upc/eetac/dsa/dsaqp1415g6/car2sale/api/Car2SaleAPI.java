package edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api;



import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

public class Car2SaleAPI {
    private final static String TAG = Car2SaleAPI.class.getName();
    private static Car2SaleAPI instance = null;
    private URL url;
    private String marca;
    private String precio;
    private String km;
    private String u;
    private String anun;
    private String usuariorecibe;

    private Car2SaleRootAPI rootAPI = null;

    private Car2SaleAPI(Context context) throws IOException, AppException {
        super();

        AssetManager assetManager = context.getAssets();
        Properties config = new Properties();
        config.load(assetManager.open("config.properties"));
        String urlHome = config.getProperty("car2sale.home");
        url = new URL(urlHome);

        Log.d("LINKS", url.toString());
        getRootAPI();
    }

    public final static Car2SaleAPI getInstance(Context context) throws AppException {
        if (instance == null)
            try {
                instance = new Car2SaleAPI(context);
            } catch (IOException e) {
                throw new AppException(
                        "Can't load configuration file");
            }
        return instance;
    }

    private void getRootAPI() throws AppException {
        Log.d(TAG, "getRootAPI()");
        rootAPI = new Car2SaleRootAPI();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Car2Sale API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, rootAPI.getLinks());
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Car2Sale API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Car2Sale Root API");
        }

    }
    public AnuncioCollection getAnuncios() throws AppException {

        Log.d(TAG, "getAnuncios()");
        AnuncioCollection anuncios = new AnuncioCollection();

        HttpURLConnection urlConnection = null;
        try {

            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("collection").getTarget()).openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            System.out.println("hemos conectado");
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Car2Sale API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());

            JSONArray jsonLinks = jsonObject.getJSONArray("links");

            parseLinks(jsonLinks, anuncios.getLinks());

            anuncios.setNewestTimestamp(jsonObject.getLong("newestTimestamp"));
            anuncios.setOldestTimestamp(jsonObject.getLong("oldestTimestamp"));
            JSONArray jsonAnuncios = jsonObject.getJSONArray("anuncios");

            for (int i = 0; i < jsonAnuncios.length(); i++) {
                Anuncio anuncio = new Anuncio();
                JSONObject jsonAnuncio = jsonAnuncios.getJSONObject(i);
                anuncio.setIdanuncio(jsonAnuncio.getString("idanuncio"));
                anuncio.setContador(jsonAnuncio.getString("contador"));
                anuncio.setTitulo(jsonAnuncio.getString("titulo"));
                anuncio.setDescripcion(jsonAnuncio.getString("descripcion"));
                anuncio.setMarca(jsonAnuncio.getString("marca"));
                anuncio.setModelo(jsonAnuncio.getString("modelo"));
                anuncio.setKm(jsonAnuncio.getString("km"));
                anuncio.setPrecio(jsonAnuncio.getString("precio"));
                anuncio.setProvincia(jsonAnuncio.getString("provincia"));
                anuncio.setLast_modified(jsonAnuncio.getLong("last_modified"));
                anuncio.setCreation_timestamp(jsonAnuncio.getLong("creation_timestamp"));
                jsonLinks = jsonAnuncio.getJSONArray("links");
                parseLinks(jsonLinks, anuncio.getLinks());
                anuncios.getAnuncios().add(anuncio);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Car2Sale API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Car2Sale Root API");
        }

        return anuncios;
    }

    private void parseLinks(JSONArray jsonLinks, Map<String, Link> map)
            throws AppException, JSONException {
        for (int i = 0; i < jsonLinks.length(); i++) {
            Link link = null;
            try {
                link = SimpleLinkHeaderParser
                        .parseLink(jsonLinks.getString(i));
            } catch (Exception e) {
                throw new AppException(e.getMessage());
            }
            String rel = link.getParameters().get("rel");
            String rels[] = rel.split("\\s");
            for (String s : rels)
                map.put(s, link);
        }
    }

}
