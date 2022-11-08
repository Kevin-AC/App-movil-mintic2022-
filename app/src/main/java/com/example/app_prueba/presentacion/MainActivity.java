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
import com.example.app_prueba.Firebase.AdaptadorLugaresFirestore;
import com.example.app_prueba.R;
import com.example.app_prueba.caso_uso.CasosUsoActividad;
import com.example.app_prueba.caso_uso.CasosUsoLocalizacion;

import com.example.app_prueba.caso_uso.CasosUsoLugar;
import com.example.app_prueba.datos.LugaresBD;
import com.example.app_prueba.modelo.Lugar;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    private CasosUsoLugar usoLugar;
    private CasosUsoActividad usoActividades;
    static final int RESULTADO_PREFERENCIAS = 0;
    private RecyclerView recyclerView;
    private static final int SOLICITUD_PERMISO_LOCALIZACION = 1;
    private CasosUsoLocalizacion usoLocalizacion;
    //base de datos sqlite
    private AdaptadorLugaresBD adaptador;
    private LugaresBD lugares;
    private AdaptadorLugaresFirestore adaptadorLugaresFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("tag","on create main");//esto me falto

        lugares = ((Aplicacion)getApplication()).lugares;
        adaptador = ((Aplicacion) getApplication()).adaptador;

        usoActividades = new CasosUsoActividad(this);
        usoLugar = new CasosUsoLugar(this,lugares,adaptador);
        usoLocalizacion = new CasosUsoLocalizacion(this,SOLICITUD_PERMISO_LOCALIZACION);

        /*recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptador);
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicion = (Integer) v.getTag();
                //recyclerView.getChildAdapterPosition(v);
                usoLugar.mostrar(posicion);
            }
        });}*/

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
                //Snackbar.make(view, R.string.mensaje_fab, Snackbar.LENGTH_LONG).setAction("Accion",null).show();
                usoLugar.nuevo();
            }
        });
        /*
        //importar lugaresDB a cloud firestore
        FirebaseFirestore firestoreDB_lugares = FirebaseFirestore.getInstance();
            for(int id=0; id<adaptador.getItemCount();id++){
            Log.d("MAIN","tamaño base datos ->"+adaptador.lugarPosicion(id));firestoreDB_lugares.collection("lugares").add(adaptador.lugarPosicion(id)
            );
        }*/
        //base dee datos en la nueve
        cargarInfoFromFirestore();
        adaptadorLugaresFirestore.startListening();
    }
    public void cargarInfoFromFirestore(){
        Query query = FirebaseFirestore.getInstance().collection("lugares").limit(50);
        FirestoreRecyclerOptions<Lugar> opciones = new FirestoreRecyclerOptions
                        .Builder<Lugar>().setQuery(query,Lugar.class).build();
        adaptadorLugaresFirestore = new AdaptadorLugaresFirestore(opciones,this);
        Log.d("TAG MAIN","cargarfirestore " + query.toString() +
                "\nrecycler"+opciones.toString());
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adaptadorLugaresFirestore);
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
            usoActividades.lanzarAcercadDe();
            return true;
        }
        if (id == R.id.menu_buscar){
            lanzarVistaLugar(null);
            Log.d("Tag main","clic a la opcion buscar");
            return true;
        }
        if (id == R.id.menu_usuario){
            usoActividades.lanzarUsuario();
            return true;
        }
        if (id == R.id.menu_mapa){
            usoActividades.lanzarMapa();
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
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int id = Integer.parseInt(entrada.getText().toString());
                                usoLugar.mostrar(id);
                            }})
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("tag MA", "onpause main ");
        usoLocalizacion.desactivar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("tag MA", "onresume main ");
       usoLocalizacion.activar();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == SOLICITUD_PERMISO_LOCALIZACION
                && grantResults.length==1 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
            usoLocalizacion.permisoConcedido();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RESULTADO_PREFERENCIAS){
            adaptador.setCursor(lugares.extraeCursor());
            adaptador.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("tag","on start main");
        adaptadorLugaresFirestore.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("tag","on stop main");
        adaptadorLugaresFirestore.stopListening();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("tag","on destroy main");
        adaptadorLugaresFirestore.stopListening();
    }



}