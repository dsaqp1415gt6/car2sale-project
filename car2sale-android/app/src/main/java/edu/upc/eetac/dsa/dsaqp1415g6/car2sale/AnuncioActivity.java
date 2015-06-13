package edu.upc.eetac.dsa.dsaqp1415g6.car2sale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Anuncio;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.AppException;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Car2SaleAPI;


public class AnuncioActivity extends Activity {
    private final static String TAG = AnuncioDetailActivity.class.getName();
    String url  = null;

    // @Override
   /* protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.libro_detail_layout);
    }*/
    private void loadAnuncio(Anuncio anuncio) {
        TextView tvDetailTitulo = (TextView) findViewById(R.id.tvTitulo);
        TextView tvDetailCoche = (TextView) findViewById(R.id.tvCoche);
        TextView tvDetailProvincia = (TextView) findViewById(R.id.tvProvincia);
        TextView tvDetailPrecio = (TextView) findViewById(R.id.tvPrecio);
        TextView tvDetailkm = (TextView) findViewById(R.id.tvkm);
        TextView tvDetailDescripcion = (TextView) findViewById(R.id.tvDescripcion);
        TextView tvDetailContador = (TextView) findViewById(R.id.tvDetailContador);
        TextView tvDetailDate = (TextView) findViewById(R.id.tvDetailDate);

        tvDetailTitulo.setText(anuncio.getTitulo());
        String marca = anuncio.getMarca();
        String modelo= anuncio.getModelo();
        tvDetailProvincia.setText(anuncio.getProvincia());
        tvDetailPrecio.setText(anuncio.getPrecio()+ " euros");
        tvDetailkm.setText(anuncio.getKm()+ " Km");
        tvDetailDescripcion.setText(anuncio.getDescripcion());
        tvDetailContador.setText("Numero de  visitas: " +anuncio.getContador());
        String coche= (marca+ " " +modelo);
        tvDetailCoche.setText(coche);
        long val = anuncio.getCreation_timestamp();
        Date date=new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        String dateText = df2.format(date);
        tvDetailDate.setText(dateText);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anuncio_detail_layout);
        url = (String) getIntent().getExtras().get("url");

        (new FetchAnuncioTask()).execute(url);
    }
    private class FetchAnuncioTask extends AsyncTask<String, Void, Anuncio> {
        private ProgressDialog pd;

        @Override
        protected Anuncio doInBackground(String... params) {
            Anuncio anuncio = null;
            try {
                anuncio = Car2SaleAPI.getInstance(AnuncioActivity.this)
                        .getAnuncio(params[0]);
            } catch (AppException e) {
                Log.d(TAG, e.getMessage(), e);
            }
            return anuncio;
        }

        @Override
        protected void onPostExecute(Anuncio result) {
            loadAnuncio(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(AnuncioActivity.this);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }
}
