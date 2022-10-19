package com.example.app_prueba.caso_uso;

import android.app.Activity;
import android.content.Intent;

import com.example.app_prueba.presentacion.AcercaDeActivity;
import com.example.app_prueba.presentacion.PreferenciasActivity;

public class CasoUsoActividad {
    protected Activity actividad;
    //constructor
    public CasoUsoActividad(Activity actividad) {
        this.actividad = actividad;
    }
    public void lanzarAcercadDe(){
        actividad.startActivity(new Intent(actividad, AcercaDeActivity.class));
    }

    public void lanzarPreferencias(int codidoSolicitud) {
        actividad.startActivityForResult(new Intent(actividad,
                PreferenciasActivity.class), codidoSolicitud);
    }

}
