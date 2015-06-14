package edu.upc.eetac.dsa.dsaqp1415g6.car2sale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import android.view.View;

public class FiltrarPreciosActivity extends Activity implements View.OnClickListener {
    private final static String TAG = FiltrarMarcasActivity.class.getName();
    String urlAnuncio=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtrar_precios_detail);
        ImageView imageView1 = (ImageView) findViewById(R.id.tvDetail1000euros);
        ImageView imageView2 = (ImageView) findViewById(R.id.tvDetail3000euros);
        ImageView imageView3 = (ImageView) findViewById(R.id.tvDetail5000euros);
        ImageView imageView4 = (ImageView) findViewById(R.id.tvDetail7000euros);
        ImageView imageView5 = (ImageView) findViewById(R.id.tvDetail10000euros);
        ImageView imageView6 = (ImageView) findViewById(R.id.tvDetail15000euros);
        ImageView imageView7 = (ImageView) findViewById(R.id.tvDetail20000euros);
        ImageView imageView8 = (ImageView) findViewById(R.id.tvDetail25000euros);
        ImageView imageView9 = (ImageView) findViewById(R.id.tvDetail30000euros);
        ImageView imageView10 = (ImageView) findViewById(R.id.tvDetail40000euros);
        ImageView imageView11 = (ImageView) findViewById(R.id.tvDetail50000euros);
        ImageView imageView12 = (ImageView) findViewById(R.id.tvDetail70000euros);
        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
        imageView6.setOnClickListener(this);
        imageView7.setOnClickListener(this);
        imageView8.setOnClickListener(this);
        imageView9.setOnClickListener(this);
        imageView10.setOnClickListener(this);
        imageView11.setOnClickListener(this);
        imageView12.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDetail1000euros:

                Intent intent1000 = new Intent(this, PrecioDetailActivity.class);
                intent1000.putExtra("precio", "1000");
                startActivity(intent1000);
                break;

            case R.id.tvDetail3000euros:
                Intent intent3000 = new Intent(this, PrecioDetailActivity.class);
                intent3000.putExtra("precio", "3000");
                startActivity(intent3000);
                break;

            case R.id.tvDetail5000euros:
                Intent intent5000 = new Intent(this, PrecioDetailActivity.class);
                intent5000.putExtra("precio", "5000");
                startActivity(intent5000);
                break;

            case R.id.tvDetail7000euros:
                Intent intent7000 = new Intent(this, PrecioDetailActivity.class);
                intent7000.putExtra("precio", "7000");
                startActivity(intent7000);
                break;

            case R.id.tvDetail10000euros:
                Intent intent10000 = new Intent(this, PrecioDetailActivity.class);
                intent10000.putExtra("precio", "10000");
                startActivity(intent10000);
                break;

            case R.id.tvDetail15000euros:
                Intent intent15000 = new Intent(this, PrecioDetailActivity.class);
                intent15000.putExtra("precio", "15000");
                startActivity(intent15000);
                break;
            case R.id.tvDetail20000euros:
                Intent intent20000 = new Intent(this, PrecioDetailActivity.class);
                intent20000.putExtra("precio", "20000");
                startActivity(intent20000);
                break;
            case R.id.tvDetail25000euros:
                Intent intent25000 = new Intent(this, PrecioDetailActivity.class);
                intent25000.putExtra("precio", "250000");
                startActivity(intent25000);
                break;
            case R.id.tvDetail30000euros:
                Intent intent30000 = new Intent(this, PrecioDetailActivity.class);
                intent30000.putExtra("precio", "30000");
                startActivity(intent30000);
                break;
            case R.id.tvDetail40000euros:
                Intent intent40000 = new Intent(this, PrecioDetailActivity.class);
                intent40000.putExtra("precio", "40000");
                startActivity(intent40000);
                break;
            case R.id.tvDetail50000euros:
                Intent intent50000 = new Intent(this, PrecioDetailActivity.class);
                intent50000.putExtra("precio", "50000");
                startActivity(intent50000);
                break;
            case R.id.tvDetail70000euros:
                Intent intent70000 = new Intent(this, PrecioDetailActivity.class);
                intent70000.putExtra("precio", "70000");
                startActivity(intent70000);
                break;



        }
    }

}
