package edu.upc.eetac.dsa.dsaqp1415g6.car2sale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import android.view.View;

public class FiltrarKmsActivity extends Activity implements View.OnClickListener {

    private final static String TAG = FiltrarKmsActivity.class.getName();
    String urlAnuncio=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtrar_kms_detail);
        ImageView imageView1 = (ImageView) findViewById(R.id.tvDetail10000km);
        ImageView imageView2 = (ImageView) findViewById(R.id.tvDetail20000km);
        ImageView imageView3 = (ImageView) findViewById(R.id.tvDetail30000km);
        ImageView imageView4 = (ImageView) findViewById(R.id.tvDetail40000km);
        ImageView imageView5 = (ImageView) findViewById(R.id.tvDetail50000km);
        ImageView imageView6 = (ImageView) findViewById(R.id.tvDetail60000km);
        ImageView imageView7 = (ImageView) findViewById(R.id.tvDetail80000km);
        ImageView imageView8 = (ImageView) findViewById(R.id.tvDetail100000km);
        ImageView imageView9 = (ImageView) findViewById(R.id.tvDetail150000km);
        ImageView imageView10 = (ImageView) findViewById(R.id.tvDetail200000km);
        ImageView imageView11 = (ImageView) findViewById(R.id.tvDetail250000km);
        ImageView imageView12 = (ImageView) findViewById(R.id.tvDetail300000km);
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
            case R.id.tvDetail10000km:

                Intent intent10000 = new Intent(this, KmDetailActivity.class);
                intent10000.putExtra("km", "10000");
                startActivity(intent10000);
                break;

            case R.id.tvDetail20000km:
                Intent intent20000 = new Intent(this, KmDetailActivity.class);
                intent20000.putExtra("km", "20000");
                startActivity(intent20000);
                break;

            case R.id.tvDetail30000km:
                Intent intent30000 = new Intent(this, KmDetailActivity.class);
                intent30000.putExtra("km", "30000");
                startActivity(intent30000);
                break;

            case R.id.tvDetail40000km:
                Intent intent40000 = new Intent(this, KmDetailActivity.class);
                intent40000.putExtra("km", "40000");
                startActivity(intent40000);
                break;

            case R.id.tvDetail50000km:
                Intent intent50000 = new Intent(this, KmDetailActivity.class);
                intent50000.putExtra("km", "50000");
                startActivity(intent50000);
                break;

            case R.id.tvDetail60000km:
                Intent intent60000 = new Intent(this, KmDetailActivity.class);
                intent60000.putExtra("km", "60000");
                startActivity(intent60000);
                break;
            case R.id.tvDetail80000km:
                Intent intent80000 = new Intent(this, KmDetailActivity.class);
                intent80000.putExtra("km", "80000");
                startActivity(intent80000);
                break;
            case R.id.tvDetail100000km:
                Intent intent100000 = new Intent(this, KmDetailActivity.class);
                intent100000.putExtra("km", "100000");
                startActivity(intent100000);
                break;
            case R.id.tvDetail150000km:
                Intent intent150000 = new Intent(this, KmDetailActivity.class);
                intent150000.putExtra("km", "150000");
                startActivity(intent150000);
                break;
            case R.id.tvDetail200000km:
                Intent intent200000 = new Intent(this, KmDetailActivity.class);
                intent200000.putExtra("km", "200000");
                startActivity(intent200000);
                break;
            case R.id.tvDetail250000km:
                Intent intent250000 = new Intent(this, KmDetailActivity.class);
                intent250000.putExtra("km", "250000");
                startActivity(intent250000);
                break;
            case R.id.tvDetail300000km:
                Intent intent300000 = new Intent(this, KmDetailActivity.class);
                intent300000.putExtra("km", "300000");
                startActivity(intent300000);
                break;



        }
    }

}
