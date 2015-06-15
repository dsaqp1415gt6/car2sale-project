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

import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Mensaje;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.MensajeCollection;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.AppException;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Car2SaleAPI;
public class MensajesDetailActivity extends ListActivity {
    private final static String TAG = MensajesDetailActivity.class.toString();
    ArrayList<Mensaje> mensajesList;
    private MensajeAdapter adapter;
    String misanuncios;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_2_sale_main);

        mensajesList = new ArrayList<Mensaje>();
        adapter = new MensajeAdapter(this, mensajesList);
        setListAdapter(adapter);
        (new FetchAnunciosTask()).execute( );
    }

    private void addMensajes(MensajeCollection mensajes){
        mensajesList.addAll(mensajes.getMensajes());
        adapter.notifyDataSetChanged();

    }




    private class FetchAnunciosTask extends
            AsyncTask<String, Void, MensajeCollection> {
        private ProgressDialog pd;

        @Override
        protected MensajeCollection doInBackground(String... params) {
            MensajeCollection mensajes = null;

            try {
                mensajes = Car2SaleAPI.getInstance(MensajesDetailActivity.this)
                        .getMensajes();


            } catch (AppException e) {
                e.printStackTrace();
            }
            return mensajes;
        }

        @Override
        protected void onPostExecute(MensajeCollection result) {
            addMensajes(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MensajesDetailActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }



    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Mensaje mensaje = mensajesList.get(position);
        Log.d(TAG, mensaje.getLinks().get("self").getTarget());
        String u=  mensaje.getLinks().get("self").getTarget();
        String idanuncio= mensaje.getAnuncio();
        String[] uf = u.split("/");
        String UrlAnuncio= uf[0]+"/"+uf[1]+"/"+uf[2]+"/"+uf[3]+"/anuncios/" +idanuncio;

        Intent intent = new Intent(this, AnuncioActivity.class);
        intent.putExtra("url", UrlAnuncio);
        startActivity(intent);
    }
}
