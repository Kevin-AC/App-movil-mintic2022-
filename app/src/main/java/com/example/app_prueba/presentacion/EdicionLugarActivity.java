package com.example.app_prueba.presentacion;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;

import com.example.app_prueba.Aplicacion;
import com.example.app_prueba.R;
import com.example.app_prueba.caso_uso.CasosUsoLugar;
import com.example.app_prueba.datos.RepositorioLugares;
import com.example.app_prueba.modelo.Lugar;
import com.example.app_prueba.modelo.TipoLugar;

public class EdicionLugarActivity extends AppCompatActivity {

    private RepositorioLugares lugares;
    private CasosUsoLugar usoLugar;
    private int pos;
    private Lugar lugar;
    private EditText nombre;
    private Spinner tipo;
    private EditText direccion;
    private EditText telefono;
    private EditText url;
    private EditText comentario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_lugar);
        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("pos", 0);
        lugares = ((Aplicacion) getApplication()).lugares;
        usoLugar = new CasosUsoLugar(this, lugares);
        lugar = lugares.elemento(pos);
        actualizaVistas();
    }
    public void actualizaVistas() {
        nombre = findViewById(R.id.nombre);
        nombre.setText(lugar.getNombre());
        direccion = findViewById(R.id.direccion);
        direccion.setText(lugar.getDireccion());
        telefono = findViewById(R.id.telefono);
        telefono.setText(Integer.toString(lugar.getTelefono()));
        url = findViewById(R.id.url);
        url.setText(lugar.getUrl());
        comentario = findViewById(R.id.comentario);
        comentario.setText(lugar.getComentario());
        tipo = findViewById(R.id.tipo);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, TipoLugar.getNombres());
        adaptador.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        tipo.setAdapter(adaptador);
        tipo.setSelection(lugar.getTipo().ordinal());
    }
}