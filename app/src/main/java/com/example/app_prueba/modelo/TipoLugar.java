package com.example.app_prueba.modelo;

import com.example.app_prueba.R;

public enum TipoLugar {
    OTROS ("Otros", R.drawable.otros),
    RESTAURANTE ("Restaurante", R.drawable.cuchilleria),
    BAR ("Bar", R.drawable.bar),
    COPAS ("Copas", R.drawable.cerveza),
    ESPECTACULO ("Espectáculo",R.drawable.spotlight),
    HOTEL ("Hotel",R.drawable.hotel),
    COMPRAS ("Compras",R.drawable.tienda),
    EDUCACION ("Educación", R.drawable.colegio),
    DEPORTE ("Deporte",R.drawable.estadio),
    BANCO ("Banco",R.drawable.bank),
    GASOLINERA ("Gasolinera", R.drawable.bombagasolina);

    private final String texto;
    private final int recurso; //referencia a archivo img del lugar

    TipoLugar(String texto, int recurso) {
        this.texto = texto;
        this.recurso = recurso;
    }

    public String getTexto() {
        return texto;
    }

    public int getRecurso() {
        return recurso;
    }

    public static String[] getNombres() {
        String[] resultado = new String[TipoLugar.values().length];
        for (TipoLugar tipo : TipoLugar.values()) {
            resultado[tipo.ordinal()] = tipo.texto;
        }
        return resultado;
    }
}
