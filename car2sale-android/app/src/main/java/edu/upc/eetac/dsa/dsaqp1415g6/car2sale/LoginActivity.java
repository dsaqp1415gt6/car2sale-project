package edu.upc.eetac.dsa.dsaqp1415g6.car2sale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.AppException;
import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Car2SaleAPI;

public class LoginActivity extends Activity {
    private final static String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        SharedPreferences prefs = getSharedPreferences("ca2sale-profile",
                Context.MODE_PRIVATE);
        String username = prefs.getString("username", null);
        String password = prefs.getString("password", null);
        // Uncomment the next two lines to test the application without login
        // each time
        // username = "alicia";
        // password = "alicia";
        if ((username != null) && (password != null)) {
            Intent intent = new Intent(this, Car2SaleMainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.login_layout);
    }

    public void signIn(View v) {
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        (new checkLoginTask()).execute(username, password);
    }

    private void startCar2SaleActivity() {
        Intent intent = new Intent(this, Car2SaleMainActivity.class);
        startActivity(intent);
        finish();
    }
    private void evaluateLogin(Boolean loginOK) {
        if (loginOK) {
            EditText etUsername = (EditText) findViewById(R.id.etUsername);
            EditText etPassword = (EditText) findViewById(R.id.etPassword);

            final String username = etUsername.getText().toString();
            final String password = etPassword.getText().toString();
            // System.out.println(username);

            SharedPreferences prefs = getSharedPreferences("car2sale-profile",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.putString("username", username);
            editor.putString("password", password);

            boolean done = editor.commit();
            if (done)
                Log.d(TAG, "preferences set");
            else
                Log.d(TAG, "preferences not set. THIS A SEVERE PROBLEM");

            startCar2SaleActivity();
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Usuario o clave incorrectos";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private class checkLoginTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog pd;

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean correctLogin = false;
            try {
                correctLogin = Car2SaleAPI.getInstance(LoginActivity.this)
                        .checkLogin(params[0], params[1]);

            } catch (AppException e) {
                e.printStackTrace();
            }
            return correctLogin;
        }


        @Override
        protected void onPostExecute(Boolean loginOK) {
            evaluateLogin(loginOK);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(LoginActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }

}
