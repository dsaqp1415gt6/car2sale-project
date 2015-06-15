package edu.upc.eetac.dsa.dsaqp1415g6.car2sale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.AppException;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Car2SaleAPI;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Favorito;



public class WriteFavoritoActivity extends Activity {
    private final static String TAG = WriteFavoritoActivity.class.getName();
    String url  = null;

    private class PostFavoritoTask extends AsyncTask<String, Void, Favorito> {
        private ProgressDialog pd;

        @Override
        protected Favorito doInBackground(String... params) {
            Favorito favorito  = null;
            try {
                favorito = Car2SaleAPI.getInstance(WriteFavoritoActivity.this)
                        .createFavorito();
            } catch (AppException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return favorito;
        }

        @Override
        protected void onPostExecute(Favorito result) {
            showMensajes(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(WriteFavoritoActivity.this);

            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anuncio_detail_layout);

        (new PostFavoritoTask()).execute( );;
    }



    private void showMensajes(Favorito result) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("  ");
        alertDialog.setMessage("     Anuncio añadido a Favoritos");
        alertDialog.setButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                salir();
            }
        });
        alertDialog.setIcon(R.drawable.car2sale_logo);
        alertDialog.show();

    }
    private void salir() {
        Intent anuncios = new Intent(this, Car2SaleMainActivity.class);
        startActivity(anuncios);
    }
}
