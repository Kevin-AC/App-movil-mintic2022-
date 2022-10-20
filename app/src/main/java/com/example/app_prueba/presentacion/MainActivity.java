package com.example.app_prueba.presentacion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.app_prueba.Aplicacion;
import com.example.app_prueba.R;
import com.example.app_prueba.caso_uso.CasoUsoActividad;
import com.example.app_prueba.caso_uso.CasosUsoLocalizacion;
import com.example.app_prueba.caso_uso.CasosUsoLugar;
import com.example.app_prueba.datos.LugaresLista;
import com.example.app_prueba.datos.RepositorioLugares;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private RepositorioLugares lugares;
    private CasosUsoLugar usoLugar;
    private CasoUsoActividad usoActividades;
    static final int RESULTADO_PREFERENCIAS = 0;

    private RecyclerView recyclerView;
    public AdaptadorLugares adaptador;

    private static final int SOLICITUD_PERMISO_LOCALIZACION = 1;
    private CasosUsoLocalizacion usoLocalizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adaptador = ((Aplicacion) getApplication()).adaptador;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);

        lugares = ((Aplicacion)getApplication()).lugares;
        usoLugar = new CasosUsoLugar(this,lugares);
        usoActividades = new CasoUsoActividad(this);
        usoLocalizacion = new CasosUsoLocalizacion(this,SOLICITUD_PERMISO_LOCALIZACION);

        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion = recyclerView.getChildAdapterPosition(v);
                usoLugar.mostrar(posicion);
            }
        });

        //barra de acciones
        Toolbar toolbar = findViewById(R.id.toolbar_Main);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout =  findViewById(R.id.toolbar_layout_Main);
        toolbar.setTitle(getTitle());
        //Boton flotante FAB circular
        /**/
        FloatingActionButton fab =  findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.mensaje_fab, Snackbar.LENGTH_LONG).setAction("Accion",null).show();
            }
        });

        /*
        RepositorioLugares lugares = new LugaresLista();
        for(int i=0;i<lugares.dimension();i++){
            Log.d("TAG main","listado de lugares"+lugares.elemento(i).toString());
        }
        */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ajustes){
            usoActividades.lanzarPreferencias(RESULTADO_PREFERENCIAS);
            return true;
        }
        if (id == R.id.acercaDe){
            //lanzarAcercaDe(null);
            usoActividades.lanzarAcercadDe();
            return true;
        }
        if (id == R.id.menu_buscar){
            lanzarVistaLugar(null);
            Log.d("Tag main","clic a la opcion buscar");
            return true;
        }
        if (id == R.id.menu_usuario){
            return true;
        }
        if (id == R.id.menu_mapa){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void lanzarVistaLugar(View view){//crea alerta de dialogo con edit text
        final EditText entrada = new EditText(this);
        entrada.setText("0");
        new AlertDialog.Builder(this)
                .setTitle(R.string.elijaId)
                .setMessage("indica id de lugar:")
                .setView(entrada)
                .setPositiveButton("Ok", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int
                                    whichButton) {
                                int id = Integer.parseInt
                                        (entrada.getText().toString());
                                usoLugar.mostrar(id);
                            }})
                .setNegativeButton("Cancelar", null)
                .show();
    }

    public void lanzarAcercaDe(View view){
        Intent abrir = new Intent(this, AcercaDeActivity.class);
        startActivity(abrir);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults)
    { super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == SOLICITUD_PERMISO_LOCALIZACION
                && grantResults.length==1
                && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                usoLocalizacion.permisoConcedido();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tag MA", "onresume main ");
        usoLocalizacion.activar();
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag MA", "onpause main ");
        usoLocalizacion.desactivar();
    }


}