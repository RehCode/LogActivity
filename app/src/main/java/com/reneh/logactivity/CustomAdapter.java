package com.reneh.logactivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class CustomAdapter extends BaseAdapter {

    private ArrayList<DataItem> data;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdapter(Context context, ArrayList<DataItem> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, null);
        }
        TextView nombreActividadText = (TextView) view.findViewById(R.id.actividadLabel);
        TextView duracionActividadText = (TextView) view.findViewById(R.id.duracionLabel);
        TextView horasActividadText = (TextView) view.findViewById(R.id.horasLabel);
        DataItem item = data.get(position);

        nombreActividadText.setText(item.actividad);
        long segundos, minutos, horas, diff=0;

        SimpleDateFormat formatTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
        Calendar actualCal = Calendar.getInstance();
        Calendar siguienteCal = Calendar.getInstance();
        StringBuilder horasString = new StringBuilder();

        if (position == 0) { // inicio registro
            try {
                actualCal.setTime(formatTimestamp.parse(item.fecha));
                duracionActividadText.setText(String.format(""));

                horasString = new StringBuilder();
                horasString.append(formatHora.format(actualCal.getTime()));
                horasString.append(" - ");
                horasActividadText.setText(horasString);
                Log.d(TAG, String.format("pos: %d, actividad: %s", position, item.actividad));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Log.d(TAG, String.format("pos: %d, actividad: %s", position, item.actividad));
                actualCal.setTime(formatTimestamp.parse(item.fecha));
                siguienteCal.setTime(formatTimestamp.parse(data.get(position - 1).fecha));
                diff = siguienteCal.getTimeInMillis() - actualCal.getTimeInMillis();

                segundos = diff / 1000 % 60;
                minutos = diff / (60 * 1000) % 60;
                horas = diff / (60 * 60 * 1000) % 24;
                duracionActividadText.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));

                horasString = new StringBuilder();
                horasString.append(formatHora.format(actualCal.getTime()));
                horasString.append(" - ");
                horasString.append(formatHora.format(siguienteCal.getTime()));;
                horasActividadText.setText(horasString);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return view;
    }
}
