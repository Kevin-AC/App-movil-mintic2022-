package com.example.app_prueba.presentacion;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.app_prueba.Aplicacion;
import com.example.app_prueba.R;
import com.example.app_prueba.caso_uso.CasosUsoLugar;
import com.example.app_prueba.datos.LugaresBD;
import com.example.app_prueba.datos.RepositorioLugares;
import com.example.app_prueba.modelo.Lugar;
import com.example.app_prueba.modelo.TipoLugar;

public class EdicionLugarActivity extends AppCompatActivity {

    //private RepositorioLugares lugares;
    private CasosUsoLugar usoLugar;
    private int pos,_id;
    private Lugar lugar;
    private EditText nombre;
    private Spinner tipo;
    private EditText direccion;
    private EditText telefono;
    private EditText url;
    private EditText comentario;
    private Toast msnToast;
    //base de datos sqlite
    private LugaresBD lugares;
    private AdaptadorLugaresBD adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edicion_lugar);
        lugares = ((Aplicacion) getApplication()).lugares;
        adaptador = ((Aplicacion) getApplication()).adaptador;
        usoLugar = new CasosUsoLugar(this, lugares,adaptador);

        Bundle extras = getIntent().getExtras();
        pos = extras.getInt("pos", -1);
        _id= extras.getInt("_id",-1);
        if (_id!=-1){setTitle("Nuevo lugar");
            lugar = lugares.elemento(_id);
        }
        else lugar = adaptador.lugarPosicion(pos); // lugares.elemento(pos);
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
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipo.setAdapter(adaptador);
        tipo.setSelection(lugar.getTipo().ordinal());
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edicion_lugar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_guardar:
                lugar.setNombre(nombre.getText().toString());
                lugar.setTipo(TipoLugar.values()[tipo.getSelectedItemPosition()]);
                lugar.setDireccion(direccion.getText().toString());
                lugar.setTelefono(Integer.parseInt(telefono.getText().toString()));
                lugar.setUrl(url.getText().toString());
                lugar.setComentario(comentario.getText().toString());
                msnToast = Toast.makeText(getApplicationContext(),"Cambios fueron guardados correctamente",Toast.LENGTH_LONG);
                msnToast.setGravity(Gravity.CENTER,0,0);
                msnToast.show();
                if (_id==-1)_id = adaptador.idPosicion(pos);
                usoLugar.guardar(_id, lugar);
                finish();
                return true;
            case R.id.accion_cancelar:
                msnToast = Toast.makeText(this,"Canceló la edición no hay cambios",Toast.LENGTH_LONG);
                msnToast.show();
                if (_id!=-1) lugares.borrar(_id);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //si ud hace clic en el boton de atras el registro queda con algunos pero con el fin de no dejarlo en la base de datos
    // sobreescriba el siguiente método

    @Override
    protected void onStop() {
        super.onStop();
        if (_id !=-1){
    //borra el registro a crear si se cumple la condición
            lugares.borrar(_id);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if (_id!=-1) lugares.borrar(_id);
        Log.d("tag","on destroy ela");
    }
}
