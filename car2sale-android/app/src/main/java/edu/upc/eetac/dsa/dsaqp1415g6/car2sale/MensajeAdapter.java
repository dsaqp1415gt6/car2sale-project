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

import edu.upc.eetac.dsa.dsaqp1415g6.car2sale.api.Mensaje;


public class MensajeAdapter extends BaseAdapter{
    private ArrayList<Mensaje> data;
    private LayoutInflater inflater;

    public MensajeAdapter(Context context, ArrayList<Mensaje> data) {
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
        TextView tvDate;
        TextView tvUsername;
        TextView tvMensaje;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_mensaje, null);
            viewHolder = new ViewHolder();
            viewHolder.tvDate = (TextView) convertView
                    .findViewById(R.id.tvDate);
            viewHolder.tvUsername = (TextView) convertView
                    .findViewById(R.id.tvUsername);
            viewHolder.tvMensaje = (TextView) convertView
                    .findViewById(R.id.tvMensaje);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String username = data.get(position).getUsuarioenvia();
        String mensaje = data.get(position).getMensaje();
        long val = data.get(position).getCreation_timestamp();
        Date date=new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        String dateText = df2.format(date);
        String fecha= ("Mensaje recevido el dia "+dateText);
        String usuario= ("Escrito por, "+username);
        viewHolder.tvDate.setText(fecha);
        viewHolder.tvUsername.setText(usuario);
        viewHolder.tvMensaje.setText(mensaje);
     ;
        return convertView;
    }
}
