package com.example.app_prueba;

import android.app.Application;

import com.example.app_prueba.datos.LugaresBD;
import com.example.app_prueba.datos.LugaresLista;
import com.example.app_prueba.datos.RepositorioLugares;
import com.example.app_prueba.modelo.GeoPunto;
import com.example.app_prueba.presentacion.AdaptadorLugares;
import com.example.app_prueba.presentacion.AdaptadorLugaresBD;

public class Aplicacion extends Application {
    public LugaresBD lugares ; //= new LugaresLista();
    public AdaptadorLugaresBD adaptador;
    //public AdaptadorLugares adaptador = new AdaptadorLugares(lugares);
    public GeoPunto posicionActual = new GeoPunto(0.0, 0.0);

    @Override
    public void onCreate() {
        super.onCreate();
        lugares = new LugaresBD(this);
        adaptador = new AdaptadorLugaresBD(lugares, lugares.extraeCursor());
    }

    public RepositorioLugares getLugares() {
        return lugares;
    }
}

//AIzaSyDrFSE58lG6BnwzIerifEYLP_d4qFNan3Q
