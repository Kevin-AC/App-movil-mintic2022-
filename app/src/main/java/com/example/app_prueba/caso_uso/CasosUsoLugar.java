package com.example.app_prueba.caso_uso;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.app_prueba.datos.RepositorioLugares;
import com.example.app_prueba.presentacion.EdicionLugarActivity;
import com.example.app_prueba.presentacion.VistaLugarActivity;

public class CasosUsoLugar {
    private Activity actividad;
    private RepositorioLugares lugares;

    ///contructor
    public CasosUsoLugar(Activity actividad, RepositorioLugares lugares) {
        this.actividad = actividad;
        this.lugares = lugares;
    }

    //opereciones o funciones de la app
    public void mostrar(int pos) {
        Intent mostrar = new Intent(actividad, VistaLugarActivity.class);
        mostrar.putExtra("pos", pos);
        actividad.startActivity(mostrar);
    }

    public void borrar(final int id) {
        new AlertDialog.Builder(actividad)
                .setTitle("Borrado de lugar")
                .setMessage("¿Seguro de eliminar este lugar?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        lugares.borrar(id);
                        actividad.finish();

                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
    public void editar(int pos) {
        Intent intent_ed_lugar = new Intent(actividad,
                EdicionLugarActivity.class);
        actividad.startActivity(intent_ed_lugar);
    }
}
