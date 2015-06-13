package edu.upc.eetac.dsa.dsaqp1415g6.car2sale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import android.view.View;


public class FiltrarMarcasActivity extends Activity implements View.OnClickListener {
    private final static String TAG = FiltrarMarcasActivity.class.getName();
    String urlAnuncio=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtrar_marcas_detail);
        ImageView imageView1 = (ImageView) findViewById(R.id.tvDetailBMW);
        ImageView imageView2 = (ImageView) findViewById(R.id.tvDetailFerrari);
        ImageView imageView3 = (ImageView) findViewById(R.id.tvDetailFord);
        ImageView imageView4 = (ImageView) findViewById(R.id.tvDetailMazda);
        ImageView imageView5 = (ImageView) findViewById(R.id.tvDetailNissan);
        ImageView imageView6 = (ImageView) findViewById(R.id.tvDetailOpel);
        ImageView imageView7 = (ImageView) findViewById(R.id.tvDetailPeugeot);
        ImageView imageView8 = (ImageView) findViewById(R.id.tvDetailRenault);
        ImageView imageView9 = (ImageView) findViewById(R.id.tvDetailSeat);
        ImageView imageView10 = (ImageView) findViewById(R.id.tvDetailPorsche);
        ImageView imageView11 = (ImageView) findViewById(R.id.tvDetailLamborghini);
        ImageView imageView12 = (ImageView) findViewById(R.id.tvDetailVolkswagen);
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
            case R.id.tvDetailBMW:

                Intent intentBMW = new Intent(this, MarcaDetailActivity.class);
                intentBMW.putExtra("marca", "BMW");
                startActivity(intentBMW);
                break;

            case R.id.tvDetailFerrari:
                Intent intentFerrari = new Intent(this, MarcaDetailActivity.class);
                intentFerrari.putExtra("marca", "Ferrari");
                startActivity(intentFerrari);
                break;

            case R.id.tvDetailFord:
                Intent intentFord = new Intent(this, MarcaDetailActivity.class);
                intentFord.putExtra("marca", "Ford");
                startActivity(intentFord);
                break;

            case R.id.tvDetailMazda:
                Intent intentMazda = new Intent(this, MarcaDetailActivity.class);
                intentMazda.putExtra("marca", "Mazda");
                startActivity(intentMazda);
                break;

            case R.id.tvDetailNissan:
                Intent intentNissan = new Intent(this, MarcaDetailActivity.class);
                intentNissan.putExtra("marca", "Nissan");
                startActivity(intentNissan);
                break;

            case R.id.tvDetailOpel:
                Intent intentOpel = new Intent(this, MarcaDetailActivity.class);
                intentOpel.putExtra("marca", "Opel");
                startActivity(intentOpel);
                break;
            case R.id.tvDetailPeugeot:
                Intent intentPeugeot = new Intent(this, MarcaDetailActivity.class);
                intentPeugeot.putExtra("marca", "Peugeot");
                startActivity(intentPeugeot);
                break;
            case R.id.tvDetailRenault:
                Intent intentRenault = new Intent(this, MarcaDetailActivity.class);
                intentRenault.putExtra("marca", "Renault");
                startActivity(intentRenault);
                break;
            case R.id.tvDetailSeat:
                Intent intentSeat = new Intent(this, MarcaDetailActivity.class);
                intentSeat.putExtra("marca", "Seat");
                startActivity(intentSeat);
                break;
            case R.id.tvDetailPorsche:
                Intent intentPorsche = new Intent(this, MarcaDetailActivity.class);
                intentPorsche.putExtra("marca", "Porsche");
                startActivity(intentPorsche);
                break;
            case R.id.tvDetailLamborghini:
                Intent intentLamborghini = new Intent(this, MarcaDetailActivity.class);
                intentLamborghini.putExtra("marca", "Lamborghini");
                startActivity(intentLamborghini);
                break;
            case R.id.tvDetailVolkswagen:
                Intent intentVolkswagen = new Intent(this, MarcaDetailActivity.class);
                intentVolkswagen.putExtra("marca", "Volkswagen");
                startActivity(intentVolkswagen);
                break;



        }
    }
}

