package com.example.app_prueba.caso_uso;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.example.app_prueba.datos.RepositorioLugares;
import com.example.app_prueba.modelo.GeoPunto;
import com.example.app_prueba.modelo.Lugar;
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
    public void editar(int pos, int codigoSolicitud) {
        Intent intent_ed_lugar = new Intent(actividad, EdicionLugarActivity.class);
        intent_ed_lugar.putExtra("pos",pos);
        actividad.startActivityForResult(intent_ed_lugar,codigoSolicitud);
    }

    public void guardar(int id, Lugar nuevoLugar){
        lugares.actualizar(id,nuevoLugar);
    }
    // INTENCIONES
    public void compartir(Lugar lugar) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT,"Observa este lugar " +lugar.getNombre() + " - " + lugar.getUrl());
        actividad.startActivity(i);
    }
    public void llamarTelefono(Lugar lugar) {
        actividad.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + lugar.getTelefono())));
    }
    public void verPgWeb(Lugar lugar) {
        actividad.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(lugar.getUrl())));
    }
    public final void verMapa(Lugar lugar) {
        double lat = lugar.getPosicion().getLatitud();
        double lon = lugar.getPosicion().getLongitud();
        Uri uri = lugar.getPosicion() != GeoPunto.SIN_POSICION ?Uri.parse("geo:" + lat + ',' + lon+"?z=18&q="+Uri.encode(lugar.getDireccion())) :Uri.parse("geo:0,0?q="+Uri.encode(lugar.getDireccion()));
        Log.d("tag casos uso lugar","vermapa " +uri + " " + Uri.encode(lugar.getDireccion())+ "\n" + lugar.getPosicion() + "geopto "+ GeoPunto.SIN_POSICION);
                actividad.startActivity(new Intent("android.intent.action.VIEW", uri));
    }

}
