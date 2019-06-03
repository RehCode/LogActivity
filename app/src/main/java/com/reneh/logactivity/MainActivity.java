package com.reneh.logactivity;

import android.app.Activity;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {

    private ArrayList<DataItem> data;
    private CustomAdapter adapter;
    private ListView listaActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.data = new ArrayList<DataItem>();
//        this.data.add(new DataItem("Inicio", System.currentTimeMillis()));

        adapter = new CustomAdapter(this, data);

        listaActividad = (ListView) findViewById(R.id.lista);
        listaActividad.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.getActividades();
    }

    public void addLog(View view) {
        EditText actividadText = (EditText) findViewById(R.id.entradaActividad);
        String actividad = actividadText.getText().toString();

        DBAdapter db = new DBAdapter(this);
        db.openDB();
        if (db.add(actividad)) {
            actividadText.setText("");
        } else {
            Toast.makeText(this, "Unable To save", Toast.LENGTH_SHORT).show();
        }

        if (actividad.equalsIgnoreCase("x")) {
            db.recreateDatabase();
        }

        db.closeDB();
        this.getActividades();
    }

    private void getActividades() {
        data.clear();

        DBAdapter db = new DBAdapter(this);
        db.openDB();

        DataItem dato = null;
        Cursor cursor = db.retrieve();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String actividad = cursor.getString(1);
            String timestamp = cursor.getString(2);

            Log.d("MainActivity", String.format("id=%d, actividad=%s, timestamp=%s", id, actividad, timestamp));

            dato = new DataItem(actividad, timestamp);
            data.add(dato);
        }
        cursor.close();
        db.closeDB();
        adapter.notifyDataSetChanged();
    }
}
