package com.example.app_prueba;

import android.app.Application;

import com.example.app_prueba.datos.LugaresLista;
import com.example.app_prueba.datos.RepositorioLugares;

public class Aplicacion extends Application {
    public RepositorioLugares lugares = new LugaresLista();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public RepositorioLugares getLugares() {
        return lugares;
    }
}
