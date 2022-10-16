package com.example.app_prueba.datos;

import com.example.app_prueba.modelo.Lugar;

public interface RepositorioLugares {
    Lugar elemento(int id);
    void agrega(Lugar lugar);
    int nuevo();
    void borrar(int id);
    int dimension();
    void actualizar(int id,Lugar lugar);

}
