package edu.upc.eetac.dsa.dsaqp1415g6.car2sale;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Anuncio;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.AnuncioCollection;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.AppException;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Car2SaleAPI;


public class Car2SaleMainActivity extends ListActivity {

    private class FetchAnunciosTask extends
            AsyncTask<Void, Void, AnuncioCollection> {
        private ProgressDialog pd;

        @Override
        protected AnuncioCollection doInBackground(Void... params) {
            AnuncioCollection anuncios = null;
            try {
                anuncios = Car2SaleAPI.getInstance(Car2SaleMainActivity.this)
                        .getAnuncios();
            } catch (AppException e) {
                e.printStackTrace();
            }
            return anuncios;
        }
        @Override
        protected void onPostExecute(AnuncioCollection result) {
            addAnuncios(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(Car2SaleMainActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }



    private final static String TAG = Car2SaleMainActivity.class.toString();
    private static final String[] items = { "lorem", "ipsum", "dolor", "sit",
            "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel",
            "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel",
            "erat", "placerat", "ante", "porttitor", "sodales", "pellentesque",
            "augue", "purus" };
    //  private ArrayAdapter<String> adapter;
    private ArrayList<Anuncio> anunciosList;

    private AnuncioAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_2_sale_main);

        anunciosList = new ArrayList<Anuncio>();
        adapter = new AnuncioAdapter(this, anunciosList);
        setListAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("car2sale-profile",
                Context.MODE_PRIVATE);
        final String username = prefs.getString("username", null);
        final String password = prefs.getString("password", null);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password
                        .toCharArray());
            }
        });
        (new FetchAnunciosTask()).execute();
    }

  /*  @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_2_sale_main);

        anunciosList = new ArrayList<Anuncio>();
        adapter = new AnuncioAdapter(this, anunciosList);
        setListAdapter(adapter);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("admin", "admin"
                        .toCharArray());
            }
        });
        (new FetchAnunciosTask()).execute();
    }
    */
    /* @Override
     protected void onListItemClick(ListView l, View v, int position, long id) {
         Libro libro = librosList.get(position);
         Log.d(TAG, libro.getLinks().get("self").getTarget());
     }*/
   /* @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Book book = booksList.get(position);
        Log.d(TAG, book.getLinks().get("self").getTarget());

        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("url", book.getLinks().get("self").getTarget());
        startActivity(intent);
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_car_2_sale_main, menu);
        return true;
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Anuncio anuncio = anunciosList.get(position);
        Log.d(TAG, anuncio.getLinks().get("self").getTarget());

        Intent intent = new Intent(this, AnuncioDetailActivity.class);
        intent.putExtra("url", anuncio.getLinks().get("self").getTarget());
        startActivity(intent);
    }

    private void addAnuncios(AnuncioCollection anuncios) {
        anunciosList.addAll(anuncios.getAnuncios());
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.FiltrarMarcas:
                Intent intentMarca = new Intent(this, FiltrarMarcasActivity.class);
                startActivity(intentMarca);
                return true;

            case R.id.FiltrarPrecio:
                Intent intentPrecios = new Intent(this, FiltrarPreciosActivity.class);
                startActivity(intentPrecios);
                return true;

            case R.id.FiltrarKm:
                Intent intentKm = new Intent(this, FiltrarKmsActivity.class);
                startActivity(intentKm);
                return true;

            case R.id.MisAnuncios:

                return true;

            case R.id.Favoritos:
              ;
                return true;

            case R.id.Mensajes:

                return true;




            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
