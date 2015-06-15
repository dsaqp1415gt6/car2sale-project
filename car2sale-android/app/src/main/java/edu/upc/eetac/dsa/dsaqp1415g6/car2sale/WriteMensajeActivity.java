package edu.upc.eetac.dsa.dsaqp1415g6.car2sale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.app.AlertDialog;

import com.google.gson.Gson;

import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.AppException;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Car2SaleAPI;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Mensaje;

public class WriteMensajeActivity extends Activity {
    private final static String TAG = WriteMensajeActivity.class.getName();
    String url  = null;


    private class PostMensajeTask extends AsyncTask<String, Void, Mensaje> {
        private ProgressDialog pd;

        @Override
        protected Mensaje doInBackground(String... params) {
            Mensaje mensaje = null;
            try {
                mensaje = Car2SaleAPI.getInstance(WriteMensajeActivity.this)
                        .createMensaje(params[0]);
            } catch (AppException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return mensaje;

        }

        @Override
        protected void onPostExecute(Mensaje result) {

            showMensajes(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {

            pd = new ProgressDialog(WriteMensajeActivity.this);

            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_mensaje);

    }

    public void cancel(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void postMensaje(View v) {
        EditText etMensaje = (EditText) findViewById(R.id.etmensaje);

        String mensaje = etMensaje.getText().toString();

        (new PostMensajeTask()).execute(mensaje);
    }

    private void showMensajes(Mensaje result) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(" ");
        alertDialog.setMessage("              Mensaje Enviado");
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
