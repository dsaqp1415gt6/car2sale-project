package edu.upc.eetac.dsa.dsaqp1415g6.car2sale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Anuncio;



public class AnuncioAdapter extends BaseAdapter {
    private  ArrayList<Anuncio> data;
    private LayoutInflater inflater;

    public AnuncioAdapter(Context context, ArrayList<Anuncio> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static class ViewHolder {
        TextView tvTitulo;
        TextView tvCoche;
        TextView tvKm;
        TextView tvProvincia;
        TextView tvDate;
        TextView tvPrecio;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_anuncio, null);
            viewHolder = new ViewHolder();
            viewHolder.tvTitulo = (TextView) convertView
                    .findViewById(R.id.tvTitulo);
            viewHolder.tvCoche = (TextView) convertView
                    .findViewById(R.id.tvCoche);
            viewHolder.tvProvincia = (TextView) convertView
                    .findViewById(R.id.tvProvincia);
            viewHolder.tvKm = (TextView) convertView
                    .findViewById(R.id.tvkm);
            convertView.setTag(viewHolder);
            viewHolder.tvPrecio = (TextView) convertView
                    .findViewById(R.id.tvPrecio);
            convertView.setTag(viewHolder);
            viewHolder.tvDate = (TextView) convertView
                    .findViewById(R.id.tvDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String titulo = data.get(position).getTitulo();
        String marca = data.get(position).getMarca();
        String modelo= data.get(position).getModelo();
        String provincia= data.get(position).getProvincia();
        String precio=data.get(position).getPrecio()+ " euros";
        String km=data.get(position).getKm()+ " Km";
        long val = data.get(position).getCreation_timestamp();
        Date date=new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        String dateText = df2.format(date);

        viewHolder.tvTitulo.setText(titulo);
        String coche= (marca+ " " +modelo);
        viewHolder.tvProvincia.setText(provincia);
        viewHolder.tvCoche.setText(coche);
        viewHolder.tvKm.setText(km);
        viewHolder.tvPrecio.setText(precio);
        viewHolder.tvDate.setText(dateText);
        return convertView;
    }
}
