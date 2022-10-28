package com.example.app_prueba.datos;

import com.example.app_prueba.modelo.Lugar;
import com.example.app_prueba.modelo.TipoLugar;

import java.util.ArrayList;
import java.util.List;


public class LugaresLista implements RepositorioLugares {
    protected List<Lugar> listaLugares;
    //constructor de la clase
    public LugaresLista() {
        listaLugares = new ArrayList<Lugar>();
        agregaEjemplos();
    }
    @Override
    public Lugar elemento(int id) {
        return listaLugares.get(id);
    }

    @Override
    public void agrega(Lugar lugar) {
        listaLugares.add(lugar);
    }

    @Override
    public int nuevo() {
        Lugar lugar = new Lugar();
        listaLugares.add(lugar);
        return listaLugares.size()-1;
    }

    @Override
    public void borrar(int id) {
        listaLugares.remove(id);

    }

    @Override
    public int dimension() {
        return listaLugares.size();
    }

    @Override
    public void actualizar(int id, Lugar lugar) {
        listaLugares.set(id,lugar);

    }
    public  void agregaEjemplos (){
        agrega(new Lugar("UIS","Calle 9#27", "none",-73.121,7.1377, TipoLugar.EDUCACION,6344000, "https://www.uis.edu.co", "Una de las universidades de Colombia",
                5));
        agrega(new Lugar("Estadio Atanasio Girardot", "Cra. 74 # 48010, Medellín, Antioquia", "none",
                -75.59013,6.256864,TipoLugar.DEPORTE, 0, "http://comunasdemedellin.com/",
                "Estadio de la ciudad de medellín",  4));
        agrega(new Lugar("Movistar Arena", "Diagonal. 61c #26-36, Bogotá, Cundinamarca","none" ,
                -74.07695,4.64888, TipoLugar.ESPECTACULO, 5470183,
                "https://movistararena.co/", "Centro de eventos en Bogotá",
                4));
        agrega(new Lugar("la estrella", "Silos, Santander", "none"
                ,-72.90982,7.2466845, TipoLugar.BANCO,0,
                "SIN DATOS", "mejor banco",
                4));
        agrega(new Lugar("Loma Mesa de Ruitoque", "Loma Mesa de Ruitoque, Floridablanca, Santander",
                "none",0,0, TipoLugar.BAR, 0, "SIN DATOS",
                "Lugar en Floridablanca, Santander",4));
    }

}
