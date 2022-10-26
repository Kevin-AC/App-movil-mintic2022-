package com.example.app_prueba;

import android.app.Application;

import com.example.app_prueba.datos.LugaresLista;
import com.example.app_prueba.datos.RepositorioLugares;
import com.example.app_prueba.modelo.GeoPunto;
import com.example.app_prueba.presentacion.AdaptadorLugares;

public class Aplicacion extends Application {
    public RepositorioLugares lugares = new LugaresLista();
    public AdaptadorLugares adaptador = new AdaptadorLugares(lugares);
    public GeoPunto posicionActual = new GeoPunto(0.0, 0.0);
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public RepositorioLugares getLugares() {
        return lugares;
    }
}

//AIzaSyDrFSE58lG6BnwzIerifEYLP_d4qFNan3Q
