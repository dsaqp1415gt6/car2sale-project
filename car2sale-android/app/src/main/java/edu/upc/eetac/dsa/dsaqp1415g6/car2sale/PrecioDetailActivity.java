package edu.upc.eetac.dsa.dsaqp1415g6.car2sale;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Anuncio;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.AnuncioCollection;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.AppException;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Car2SaleAPI;


public class PrecioDetailActivity extends ListActivity {
    private final static String TAG = PrecioDetailActivity.class.toString();
    ArrayList<Anuncio> anunciosList;
    private AnuncioAdapter adapter;
    String precio;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_2_sale_main);
        precio = (String) getIntent().getExtras().get("precio");

        anunciosList = new ArrayList<Anuncio>();
        adapter = new AnuncioAdapter(this, anunciosList);
        setListAdapter(adapter);
        (new FetchAnunciosTask()).execute(precio);
    }

    private void addAnuncios(AnuncioCollection anuncios){
        anunciosList.addAll(anuncios.getAnuncios());
        adapter.notifyDataSetChanged();

    }




    private class FetchAnunciosTask extends
            AsyncTask<String, Void, AnuncioCollection> {
        private ProgressDialog pd;

        @Override
        protected AnuncioCollection doInBackground(String... params) {
            AnuncioCollection anuncios = null;

            try {
                anuncios = Car2SaleAPI.getInstance(PrecioDetailActivity.this)
                        .getPrecio(params[0]);


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
            pd = new ProgressDialog(PrecioDetailActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }



    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Anuncio anuncio = anunciosList.get(position);
        Log.d(TAG, anuncio.getLinks().get("self").getTarget());

        Intent intent = new Intent(this, AnuncioDetailActivity.class);
        intent.putExtra("url", anuncio.getLinks().get("self").getTarget());
        startActivity(intent);
    }

}
