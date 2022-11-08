package com.example.app_prueba.caso_uso;

import android.app.Activity;
import android.content.Intent;

import com.example.app_prueba.presentacion.AcercaDeActivity;
import com.example.app_prueba.presentacion.MapaActivity;
import com.example.app_prueba.presentacion.PreferenciasActivity;
import com.example.app_prueba.presentacion.UsuarioActivity;

public class CasosUsoActividad {
    protected Activity actividad;
    //constructor
    public CasosUsoActividad(Activity actividad) {
        this.actividad = actividad;
    }
    public void lanzarAcercadDe(){
        actividad.startActivity(new Intent(actividad, AcercaDeActivity.class));
    }

    public void lanzarPreferencias(int codidoSolicitud) {
        actividad.startActivityForResult(new Intent(actividad,PreferenciasActivity.class), codidoSolicitud);
    }
    public void lanzarMapa() {
        actividad.startActivity(new Intent(actividad, MapaActivity.class));
    }

    public void lanzarUsuario(){ actividad.startActivity(new
            Intent(actividad, UsuarioActivity.class)); }
}
